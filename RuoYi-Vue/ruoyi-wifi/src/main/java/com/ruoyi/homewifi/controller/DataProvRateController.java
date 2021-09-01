package com.ruoyi.homewifi.controller;

import java.util.List;

import com.ruoyi.homewifi.vo.ProvRateVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.homewifi.domain.DataProvRate;
import com.ruoyi.homewifi.service.IDataProvRateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 分省份四率统计Controller
 * 
 * @author z
 * @date 2021-08-25
 */
@RestController
@RequestMapping("/homewifi/provrate")
public class DataProvRateController extends BaseController
{
    @Autowired
    private IDataProvRateService dataProvRateService;

    /**
     * 查询分省份四率统计列表
     */
    @PreAuthorize("@ss.hasPermi('homewifi:provrate:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProvRateVo provRateVo)
    {
        startPage();
        List<DataProvRate> list = dataProvRateService.selectDataProvRateList(provRateVo);
        return getDataTable(list);
    }

    /**
     * 导出分省份四率统计列表
     */
    @PreAuthorize("@ss.hasPermi('homewifi:provrate:export')")
    @Log(title = "分省份四率统计", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ProvRateVo provRateVo)
    {
        List<DataProvRate> list = dataProvRateService.selectDataProvRateList(provRateVo);
        ExcelUtil<DataProvRate> util = new ExcelUtil<DataProvRate>(DataProvRate.class);
        return util.exportExcel(list, "分省份四率统计数据");
    }













    /**
     * 获取分省份四率统计详细信息
     */
    /*@PreAuthorize("@ss.hasPermi('homewifi:provrate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(dataProvRateService.selectDataProvRateById(id));
    }*/

    /**
     * 新增分省份四率统计
     */
    /*@PreAuthorize("@ss.hasPermi('homewifi:provrate:add')")
    @Log(title = "分省份四率统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataProvRate dataProvRate)
    {
        return toAjax(dataProvRateService.insertDataProvRate(dataProvRate));
    }*/

    /**
     * 修改分省份四率统计
     */
    /*@PreAuthorize("@ss.hasPermi('homewifi:provrate:edit')")
    @Log(title = "分省份四率统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataProvRate dataProvRate)
    {
        return toAjax(dataProvRateService.updateDataProvRate(dataProvRate));
    }*/

    /**
     * 删除分省份四率统计
     */
    /*@PreAuthorize("@ss.hasPermi('homewifi:provrate:remove')")
    @Log(title = "分省份四率统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dataProvRateService.deleteDataProvRateByIds(ids));
    }*/
}
