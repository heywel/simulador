package com.example.application.views.utilViews.gridExport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExporterProperties {
    private String title;
    private String colorTitle;
    private String colorBackgroundTitle;
    private String colorBackgroundHeaders;
    private String colorTextHeader;
}
