package com.hana.mapper;

import com.hana.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BrandMapper extends Mapper<Brand> {

    /**
     * 新增商品分类和品牌的中间表数据
     * @param cid 商品分类 id
     * @param bid 品牌 id
     */
    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES(#{cid}, #{bid})")
    void insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);




    /**
     * 通过品牌 id 删除中间表
     * @param bid
     */
    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{bid}")
    void deleteCategoryAndBrandByBid(Long bid);


    /**
     * 根据分类 id 查询品牌信息
     * @param cid
     * @return
     */
    @Select("SELECT b.* from tb_brand b INNER JOIN tb_category_brand cb on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<Brand> selectBrandByCid(Long cid);

}
