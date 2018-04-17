package kpi.tef.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ItemDto {
    private long id;
    private String barcode;
    private String description;

    @Override
    public String toString() {
        return "id=" + id +
                ", barcode='" + barcode + '\'' +
                ", description='" + description + '\'';
    }
}
