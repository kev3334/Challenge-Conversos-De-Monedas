package DTOs;

import java.util.Map;

public record ConversorDTO(String baseCode, Map<String, Double> conversionRates) {
}
