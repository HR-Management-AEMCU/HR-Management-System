package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCompanyResponseDto {
    private Long companyId;
    private String companyName;
    private String companyLogoUrl;
    //private List<String> companyDirectories;
}
