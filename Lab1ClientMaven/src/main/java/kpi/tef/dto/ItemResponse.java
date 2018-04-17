package kpi.tef.dto;

import lombok.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ItemResponse {
    private List<ItemDto> itemDtos;
}
