package com.ruoyi.homewifi.controller;

import java.util.List;

import com.ruoyi.homewifi.vo.CityRateVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.homewifi.domain.DataCityRate;
import com.ruoyi.homewifi.service.IDataCityRateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 分地市四率统计Controller
 * 
 * @author z
 * @date 2021-08-25
 */
@RestController
@RequestMapping("/homewifi/cityrate")
public class DataCityRateController extends BaseController
{
    @Autowired
    private IDataCityRateService dataCityRateService;

    /**
     * 查询分地市四率统计列表
     */
    @PreAuthorize("@ss.hasPermi('homewifi:cityrate:list')")
    @GetMapping("/list")
    public TableDataInfo list(CityRateVo cityRateVo)
    {
        startPage();
        System.out.println(cityRateVo.toString());
        List<DataCityRate> list = dataCityRateService.selectDataCityRateList(cityRateVo);
        return getDataTable(list);
    }

    /**
     * 导出分地市四率统计列表
     */
    @PreAuthorize("@ss.hasPermi('homewifi:cityrate:export')")
    @Log(title = "分地市四率统计", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CityRateVo cityRateVo)
    {
        List<DataCityRate> list = dataCityRateService.selectDataCityRateList(cityRateVo);
        ExcelUtil<DataCityRate> util = new ExcelUtil<DataCityRate>(DataCityRate.class);
        return util.exportExcel(list, "分地市四率统计数据");
    }

    /**
     * 获取分地市四率统计详细信息
     */
    /*@PreAuthorize("@ss.hasPermi('homewifi:cityrate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(dataCityRateService.selectDataCityRateById(id));
    }*/

    /**
     * 新增分地市四率统计
     */
    /*@PreAuthorize("@ss.hasPermi('homewifi:cityrate:add')")
    @Log(title = "分地市四率统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataCityRate dataCityRate)
    {
        return toAjax(dataCityRateService.insertDataCityRate(dataCityRate));
    }*/

    /**
     * 修改分地市四率统计
     */
    /*@PreAuthorize("@ss.hasPermi('homewifi:cityrate:edit')")
    @Log(title = "分地市四率统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataCityRate dataCityRate)
    {
        return toAjax(dataCityRateService.updateDataCityRate(dataCityRate));
    }*/

    /**
     * 删除分地市四率统计
     */
    /*@PreAuthorize("@ss.hasPermi('homewifi:cityrate:remove')")
    @Log(title = "分地市四率统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dataCityRateService.deleteDataCityRateByIds(ids));
    }*/
}
