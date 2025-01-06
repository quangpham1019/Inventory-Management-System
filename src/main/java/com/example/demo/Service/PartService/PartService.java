package com.example.demo.Service.PartService;

import com.example.demo.Domain.Part;
import com.example.demo.Service.CommonService.CommonService;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface PartService extends CommonService<Part, Long> {
    List<Part> listAllByKeyword(String keyword);
}
