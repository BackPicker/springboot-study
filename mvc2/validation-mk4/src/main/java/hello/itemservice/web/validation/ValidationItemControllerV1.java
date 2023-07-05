package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.relation.RelationSupport;
import javax.servlet.annotation.ServletSecurity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/validation/v1/items")
@RequiredArgsConstructor
@Slf4j
public class ValidationItemControllerV1 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v1/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v1/addForm";
    }

    //    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, Model model, RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();

        //오류 검증 시작
        if(!StringUtils.hasText(item.getItemName())) {
            errors.put("itemName", "상품 이름은 필수입니다");
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 10000000) {
            errors.put("price", "가격은 1,000원 ~ 1,000,000원 까지 허용합니다");
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.put("quantity", "수량은 최대 9,999까지 허용합니다");
        }
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
                errors.put("globalErrors", "가격 + 수량의 합은 최대 10,000원 이상이어야 합니다, 현재 가격 : " + resultPrice);

            }
        }

        if(!errors.isEmpty()) {
            log.info("log data = {}", errors);

            model.addAttribute("errors", errors);

            return "validation/v1/addForm";
        }

        //오류가 없을 경우
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {


        //오류 검증 시작
        if(!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다"));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 10000000) {
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000원 ~ 1,000,000원 까지 허용합니다"));
        }

        if(item.getQuantity() == null || item.getQuantity() > 9999) {
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999까지 허용합니다"));
        }
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
                bindingResult.addError(
                        new ObjectError("item", "가격 + 수량의 합은 최대 10,000원 이상이어야 합니다, 현재 가격 : " + resultPrice));
            }
        }

        if(bindingResult.hasErrors()) {
            log.info("log data = {}", bindingResult);

            return "validation/v1/addForm";
        }

        //오류가 없을 경우
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v1/items/{itemId}";
    }

}

