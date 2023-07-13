package com.hana.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hana.Brand;
import com.hana.mapper.BrandMapper;
import com.hana.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 根据查询条件分页并排序查询品牌信息
     *
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {

        // 初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        // 根据name模糊查询，或者根据首字母查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }

        // 添加分页条件
        PageHelper.startPage(page, rows);

        // 添加排序条件
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        List<Brand> brands = this.brandMapper.selectByExample(example);
        // 包装成pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        // 包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }



    /**
     * 新增品牌
     *
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {

        // 先新增brand
        this.brandMapper.insertSelective(brand);

        // 在新增中间表
        cids.forEach(cid -> {
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        });
    }







    /**
     * 更新品牌
     * @param cids
     * @param brand
     */
    @Transactional
    public void updateBrand(List<Long> cids, Brand brand) {
        // 先更新 Brand
        brandMapper.updateByPrimaryKey(brand);
        // 通过品牌 id 删除中间表
        brandMapper.deleteCategoryAndBrandByBid(brand.getId());
        // 再新增中间表
        for (Long cid : cids) {
            brandMapper.insertCategoryAndBrand(cid, brand.getId());
        }
    }



    /**
     * 删除品牌
     * @param bid
     */
    @Transactional
    public void deleteBrand(Long bid) {
        // 通过品牌 id 删除中间表
        brandMapper.deleteCategoryAndBrandByBid(bid);
        // 删除品牌
        brandMapper.deleteByPrimaryKey(bid);
    }


    /**
     * 根据分类 id 查询品牌信息
     * @param cid
     * @return
     */
    public List<Brand> queryBrandsByCid(Long cid) {

        return this.brandMapper.selectBrandByCid(cid);
    }

    /**
     * 根据品牌 id 查询品牌
     * @param id
     * @return
     */
    public Brand queryBrandById(Long id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        return brand;
    }



}