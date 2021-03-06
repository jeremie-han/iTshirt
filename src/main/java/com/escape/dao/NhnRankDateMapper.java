package com.escape.dao;

import com.escape.model.NhnRankDate;
import com.escape.model.NhnRankDateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NhnRankDateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nhn_rank_date
     *
     * @mbggenerated Fri Jun 28 14:32:32 KST 2019
     */
    int countByExample(NhnRankDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nhn_rank_date
     *
     * @mbggenerated Fri Jun 28 14:32:32 KST 2019
     */
    int deleteByExample(NhnRankDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nhn_rank_date
     *
     * @mbggenerated Fri Jun 28 14:32:32 KST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nhn_rank_date
     *
     * @mbggenerated Fri Jun 28 14:32:32 KST 2019
     */
    int insert(NhnRankDate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nhn_rank_date
     *
     * @mbggenerated Fri Jun 28 14:32:32 KST 2019
     */
    int insertSelective(NhnRankDate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nhn_rank_date
     *
     * @mbggenerated Fri Jun 28 14:32:32 KST 2019
     */
    List<NhnRankDate> selectByExample(NhnRankDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nhn_rank_date
     *
     * @mbggenerated Fri Jun 28 14:32:32 KST 2019
     */
    NhnRankDate selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nhn_rank_date
     *
     * @mbggenerated Fri Jun 28 14:32:32 KST 2019
     */
    int updateByExampleSelective(@Param("record") NhnRankDate record, @Param("example") NhnRankDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nhn_rank_date
     *
     * @mbggenerated Fri Jun 28 14:32:32 KST 2019
     */
    int updateByExample(@Param("record") NhnRankDate record, @Param("example") NhnRankDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nhn_rank_date
     *
     * @mbggenerated Fri Jun 28 14:32:32 KST 2019
     */
    int updateByPrimaryKeySelective(NhnRankDate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nhn_rank_date
     *
     * @mbggenerated Fri Jun 28 14:32:32 KST 2019
     */
    int updateByPrimaryKey(NhnRankDate record);
}