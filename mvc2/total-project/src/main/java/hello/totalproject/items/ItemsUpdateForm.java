package hello.totalproject.items;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ItemsUpdateForm {

  private Long    id;
  @NotBlank
  private String  itemName;
  @NotNull
  @Range(min = 1000, max = 1000000)
  private Integer price;
  @NotNull
  private Integer quantity;

}
