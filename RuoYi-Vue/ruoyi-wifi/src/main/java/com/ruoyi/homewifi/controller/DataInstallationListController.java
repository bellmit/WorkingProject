package com.ruoyi.homewifi.controller;

import java.util.List;
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
import com.ruoyi.homewifi.domain.DataInstallationList;
import com.ruoyi.homewifi.service.IDataInstallationListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 数据湖工单下发Controller
 * 
 * @author ruoyi
 * @date 2021-08-11
 */
@RestController
@RequestMapping("/homewifi/installationlisti")
public class DataInstallationListController extends BaseController
{
    @Autowired
    private IDataInstallationListService dataInstallationListService;

    /**
     * 查询数据湖工单下发列表
     */
    @PreAuthorize("@ss.hasPermi('homewifi:installationlisti:list')")
    @GetMapping("/list")
    public TableDataInfo list(DataInstallationList dataInstallationList)
    {
        startPage();
        List<DataInstallationList> list = dataInstallationListService.selectDataInstallationListList(dataInstallationList);
        return getDataTable(list);
    }

    /**
     * 导出数据湖工单下发列表
     */
    @PreAuthorize("@ss.hasPermi('homewifi:installationlisti:export')")
    @Log(title = "数据湖工单下发", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DataInstallationList dataInstallationList)
    {
        List<DataInstallationList> list = dataInstallationListService.selectDataInstallationListList(dataInstallationList);
        ExcelUtil<DataInstallationList> util = new ExcelUtil<DataInstallationList>(DataInstallationList.class);
        return util.exportExcel(list, "数据湖工单下发数据");
    }

    /**
     * 获取数据湖工单下发详细信息
     */
    @PreAuthorize("@ss.hasPermi('homewifi:installationlisti:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(dataInstallationListService.selectDataInstallationListById(id));
    }

    /**
     * 新增数据湖工单下发
     */
    @PreAuthorize("@ss.hasPermi('homewifi:installationlisti:add')")
    @Log(title = "数据湖工单下发", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataInstallationList dataInstallationList)
    {
        return toAjax(dataInstallationListService.insertDataInstallationList(dataInstallationList));
    }

    /**
     * 修改数据湖工单下发
     */
    @PreAuthorize("@ss.hasPermi('homewifi:installationlisti:edit')")
    @Log(title = "数据湖工单下发", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataInstallationList dataInstallationList)
    {
        return toAjax(dataInstallationListService.updateDataInstallationList(dataInstallationList));
    }

    /**
     * 删除数据湖工单下发
     */
    @PreAuthorize("@ss.hasPermi('homewifi:installationlisti:remove')")
    @Log(title = "数据湖工单下发", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dataInstallationListService.deleteDataInstallationListByIds(ids));
    }
}
