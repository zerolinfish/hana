package com.hana.controller;

import com.hana.Category;
import com.hana.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父id查询子节点
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam("pid") Long pid) {

        if (pid == null || pid.longValue() < 0) {
            // 响应400，相当于ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return ResponseEntity.badRequest().build();
        }
        List<Category> categories = this.categoryService.queryCategoriesByPid(pid);
        if (CollectionUtils.isEmpty(categories)) {
            // 响应404
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categories);
    }




    /**
     * 根据品牌 Id 查询品牌分类
     * @param bid
     * @return
     */
    @GetMapping("/bid/{bid}")
    public ResponseEntity<List<Category>> queryCategoryByBrandId(@PathVariable("bid") Long bid) {
        if (bid == null || bid.longValue() < 0) {
            return ResponseEntity.badRequest().build(); // 响应 400
        }
        List<Category> categories = categoryService.queryCategoryByBrandId(bid);
        if(CollectionUtils.isEmpty(categories)) {
            return ResponseEntity.notFound().build(); // 响应 404
        }
        return ResponseEntity.ok(categories);
    }



    @GetMapping("names")
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){

        List<String> names = this.categoryService.queryNamesByIds(ids);
        if (CollectionUtils.isEmpty(names)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }

}
