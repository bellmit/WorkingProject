package com.ruoyi.homewifi.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.homewifi.district.DistrictDirc;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.homewifi.domain.DataReport;
import com.ruoyi.homewifi.service.IDataReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 竣工报告查询Controller
 * 
 * @author zyk
 * @date 2021-08-19
 */
@RestController
@RequestMapping("/homewifi/report")
public class DataReportController extends BaseController
{
    @Autowired
    private IDataReportService dataReportService;

    /**
     * 查询竣工报告查询列表
     */
    @PreAuthorize("@ss.hasPermi('homewifi:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(DataReport dataReport)
    {
        startPage();
        System.out.println(dataReport.toString());
        List<DataReport> list = dataReportService.selectDataReportList(dataReport);
        return getDataTable(list);

        //TODO：8月24任务：
        // 1.报告页面联调(ok)、2.四率查询建表(ok) & 代码生成 (ok) &数据权限(ing) & 分页 (ing)、3.写四率计算业务代码、4.数据湖数据入库代码
        // 5.配饰redis读取城市对应的丰联编号再去写ES查询获取新增eLink终端数
        // 6.Dl那边的建表字段需要修改、7.数据字典里的数代表的含义需要确定、
    }



    /**
     * 导出竣工报告查询列表
     */
    @PreAuthorize("@ss.hasPermi('homewifi:report:export')")
    @Log(title = "竣工报告查询", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DataReport dataReport)
    {
        List<DataReport> list = dataReportService.selectDataReportList(dataReport);
        ExcelUtil<DataReport> util = new ExcelUtil<DataReport>(DataReport.class);
        return util.exportExcel(list, "竣工报告查询数据");
    }


    /**
     * 查询省份列表
     */
    @GetMapping("/provlist")
    public List provList()
    {
        return dataReportService.getProvList();
    }

    /**
     * 查询某一省份对应的城市列表
     */
    @GetMapping("/citylist")
    public List cityList(String wProvId)
    {
        return dataReportService.getCityList(wProvId);
    }



    /**
     * 获取竣工报告查询详细信息
     */
    /*@PreAuthorize("@ss.hasPermi('homewifi:report:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(dataReportService.selectDataReportById(id));
    }*/

    /**
     * 新增竣工报告查询
     */
    /*@PreAuthorize("@ss.hasPermi('homewifi:report:add')")
    @Log(title = "竣工报告查询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataReport dataReport)
    {
        return toAjax(dataReportService.insertDataReport(dataReport));
    }*/

    /**
     * 修改竣工报告查询
     */
    /*@PreAuthorize("@ss.hasPermi('homewifi:report:edit')")
    @Log(title = "竣工报告查询", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataReport dataReport)
    {
        return toAjax(dataReportService.updateDataReport(dataReport));
    }*/

    /**
     * 删除竣工报告查询
     */
    /*@PreAuthorize("@ss.hasPermi('homewifi:report:remove')")
    @Log(title = "竣工报告查询", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dataReportService.deleteDataReportByIds(ids));
    }*/
}
