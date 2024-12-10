package test.dto;

import java.util.List;

public record SlideshowDto(Long id, String name, List<Long> imageIds) {
}
