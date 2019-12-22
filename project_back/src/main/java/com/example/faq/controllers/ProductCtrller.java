package com.example.faq.controllers;

import com.example.faq.dto.ProductCreateDto;
import com.example.faq.dto.ProductEditDto;
import com.example.faq.models.ApiResult;
import com.example.faq.models.Product;
import com.example.faq.services.DocService;
import com.example.faq.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/api/product")
public class ProductCtrller {

    @Autowired
    private ProductService productService;
    @Autowired
    private DocService docService;

    @GetMapping("dropDownList")
    @ResponseBody
    public ApiResult productDropDownlist() {

        ApiResult apiResult = new ApiResult();
        ArrayList<Map<String,Object>> datas = new ArrayList<>();

        ArrayList<Product> products = productService.findProducts();
        if(products==null||products.size()==0){
            apiResult.setStatus(501);
            apiResult.setMsg("列表返回失败或者不存在产品");
            return apiResult;
        }
        for(Product product:products){
            Map<String,Object> data=new LinkedHashMap<>();
            data.put("id", product.getId());
            data.put("name", product.getName());

            datas.add(data);
        }

        apiResult.setStatus(200);
        apiResult.setData(datas);
        apiResult.setMsg("列表返回成功");
        return apiResult;
    }

    @GetMapping("asideList")
    @ResponseBody
    public ApiResult productAsideList() {
        ApiResult apiResult = new ApiResult();
        ArrayList<Product> products = productService.findProducts();

        if(products==null||products.size()==0){
            apiResult.setStatus(501);
            apiResult.setMsg("列表返回失败或者不存在产品");
            return apiResult;
        }
        Map<String,Object> data=new LinkedHashMap<>();
        data.put("total",products.size());
        ArrayList<Map <String,Object>> arr=new ArrayList<>();

        for(Product product: products){
            Map <String,Object> item = new LinkedHashMap<>();
            item.put("productId",product.getId());
            item.put("name", product.getName());
            item.put("color", product.getColor());

            Map<String, Object> deptQuery = new LinkedHashMap<>();
            deptQuery.put("productId",product.getId());
            // 1、获取designNum设计部门文档数量
            deptQuery.put("deptBelong",0);
            item.put("designNum",docService.getDeptBelongNumByQuery(deptQuery));

            // 2、获取codeNum开发部门文档数量
            deptQuery.put("deptBelong",1);
            item.put("codeNum",docService.getDeptBelongNumByQuery(deptQuery));

            // 3、获取implementNum实施部门文档数量
            deptQuery.put("deptBelong",2);
            item.put("implementNum",docService.getDeptBelongNumByQuery(deptQuery));

            // 4、获取testNum测试部门文档数量
            deptQuery.put("deptBelong",3);
            item.put("testNum",docService.getDeptBelongNumByQuery(deptQuery));

            arr.add(item);
        }
        data.put("arr",arr);

        apiResult.setStatus(200);
        apiResult.setData(data);
        apiResult.setMsg("列表返回成功");
        return apiResult;
    }

    @GetMapping("select")
    @ResponseBody
    public ApiResult selectProductById(Integer productId) {
        ApiResult apiResult = new ApiResult();
        Product product = productService.findProductById(productId);

        if(product==null){
            apiResult.setStatus(501);
            apiResult.setMsg("产品内容返回失败或者不存在产品");
            return apiResult;
        }

        Map<String,Object> data=new LinkedHashMap<>();
        data.put("productName",product.getName());
        data.put("imageUrl",product.getImageUrl());
        data.put("describeInfo",product.getDescribeInfo());

        apiResult.setStatus(200);
        apiResult.setData(data);
        apiResult.setMsg("产品内容返回成功");
        return apiResult;
    }

    @RequestMapping("create")
    @ResponseBody
    public ApiResult createProduct(@RequestBody ProductCreateDto productCreateDto) {
        ApiResult apiResult = new ApiResult();
        Date now=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tablename=dateFormat.format(now);

        productCreateDto.setCreatedTime(tablename);

        if(productService.addProduct(productCreateDto) == 0){
            apiResult.setStatus(501);
            apiResult.setMsg("新建产品失败");
             return apiResult;
        }

        apiResult.setStatus(200);
        apiResult.setMsg("新建产品成功");
        return apiResult;
    }

    @RequestMapping("update")
    @ResponseBody
    public ApiResult updateProduct(@RequestBody ProductEditDto productEditDto) {
        ApiResult apiResult = new ApiResult();
        Date now=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tablename=dateFormat.format(now);

        productEditDto.setCreatedTime(tablename);

        if(productService.modifyProduct(productEditDto) == 0){
            apiResult.setStatus(501);
            apiResult.setMsg("修改产品失败");
            return apiResult;
        }

        apiResult.setStatus(200);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("productId",productEditDto.getId());
        apiResult.setData(map);
        apiResult.setMsg("修改产品成功");
        return apiResult;
    }

    @GetMapping("delete")
    @ResponseBody
    public ApiResult deleteProduct(Integer productId) {
        ApiResult apiResult = new ApiResult();

        // 1、删除产品的同时将所有有关的文档软删除
        if(productService.deleteProduct(productId) == 0){
            apiResult.setStatus(501);
            apiResult.setMsg("数据库更新失败");
            return apiResult;
        }
        docService.deleteDocByProductId(productId);
        apiResult.setStatus(200);
        apiResult.setMsg("删除成功");
        return apiResult;
    }
}
