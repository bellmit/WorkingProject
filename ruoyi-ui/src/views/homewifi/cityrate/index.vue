<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="统计时间">
        <el-date-picker
          v-model="queryParams.startDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择日期">
        </el-date-picker> -
        <el-date-picker
          v-model="queryParams.endDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="省份">
        <el-select v-model="queryParams.provName" clearable placeholder="请选择省份" class="handle-select mr10" @change="getCity()">
          <el-option v-for="item in provList" :key="item.key" :label="item.value" :value="item.key"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="地市">
        <el-select v-model="queryParams.cityName" clearable placeholder="请选择地市" class="handle-select mr10">
          <el-option v-for="item in cityList" :key="item.key" :label="item.value" :value="item.key"></el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item label="有效报告数" prop="effectiveSum">-->
<!--        <el-input-->
<!--          v-model="queryParams.effectiveSum"-->
<!--          placeholder="请输入有效报告数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="新增礼包数" prop="newGiftSum">-->
<!--        <el-input-->
<!--          v-model="queryParams.newGiftSum"-->
<!--          placeholder="请输入新增礼包数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="新增含终端礼包数" prop="newTermiGiftSum">-->
<!--        <el-input-->
<!--          v-model="queryParams.newTermiGiftSum"-->
<!--          placeholder="请输入新增含终端礼包数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="新增纯服务礼包数" prop="newServiGiftSum">-->
<!--        <el-input-->
<!--          v-model="queryParams.newServiGiftSum"-->
<!--          placeholder="请输入新增纯服务礼包数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="新增e_link/e_OS终端数" prop="newElinkSum">-->
<!--        <el-input-->
<!--          v-model="queryParams.newElinkSum"-->
<!--          placeholder="请输入新增e_link/e_OS终端数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="交付地一致数" prop="sameAreaSum">-->
<!--        <el-input-->
<!--          v-model="queryParams.sameAreaSum"-->
<!--          placeholder="请输入交付地一致数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="测速达标数" prop="wifiCheckedSum">-->
<!--        <el-input-->
<!--          v-model="queryParams.wifiCheckedSum"-->
<!--          placeholder="请输入测速达标数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="报告分享数" prop="shareSum">-->
<!--        <el-input-->
<!--          v-model="queryParams.shareSum"-->
<!--          placeholder="请输入报告分享数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="小翼管家分享数" prop="yiShareSum">-->
<!--        <el-input-->
<!--          v-model="queryParams.yiShareSum"-->
<!--          placeholder="请输入小翼管家分享数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="短信分享数" prop="messageShareSum">-->
<!--        <el-input-->
<!--          v-model="queryParams.messageShareSum"-->
<!--          placeholder="请输入短信分享数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="微信分享数" prop="wechatShareSum">-->
<!--        <el-input-->
<!--          v-model="queryParams.wechatShareSum"-->
<!--          placeholder="请输入微信分享数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="其他分享数" prop="otherShareSum">-->
<!--        <el-input-->
<!--          v-model="queryParams.otherShareSum"-->
<!--          placeholder="请输入其他分享数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="有效报告匹配率" prop="effectiveReportRate">-->
<!--        <el-input-->
<!--          v-model="queryParams.effectiveReportRate"-->
<!--          placeholder="请输入有效报告匹配率"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="交付地一致率" prop="sameAreaRate">-->
<!--        <el-input-->
<!--          v-model="queryParams.sameAreaRate"-->
<!--          placeholder="请输入交付地一致率"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="WiFi测速达标率" prop="wifiCheckedRate">-->
<!--        <el-input-->
<!--          v-model="queryParams.wifiCheckedRate"-->
<!--          placeholder="请输入WiFi测速达标率"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="终端稽核率" prop="elinkCheckedRate">-->
<!--        <el-input-->
<!--          v-model="queryParams.elinkCheckedRate"-->
<!--          placeholder="请输入终端稽核率"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="报告分享率" prop="shareRate">-->
<!--        <el-input-->
<!--          v-model="queryParams.shareRate"-->
<!--          placeholder="请输入报告分享率"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="success" icon="el-icon-search" size="mini" @click="handleExport">+生成excel</el-button>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['homewifi:cityrate:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['homewifi:cityrate:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['homewifi:cityrate:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
		  :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['homewifi:cityrate:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cityrateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="省份" align="center" prop="provName" />
      <el-table-column label="地市" align="center" prop="cityName" />
      <el-table-column label="有效报告数" align="center" prop="effectiveSum" />
      <el-table-column label="新增礼包数" align="center" prop="newGiftSum" />
      <el-table-column label="新增含终端礼包数" align="center" prop="newTermiGiftSum" />
      <el-table-column label="新增纯服务礼包数" align="center" prop="newServiGiftSum" />
      <el-table-column label="新增e_link/e_OS终端数" align="center" prop="newElinkSum" />
      <el-table-column label="交付地一致数" align="center" prop="sameAreaSum" />
      <el-table-column label="测速达标数" align="center" prop="wifiCheckedSum" />
      <el-table-column label="报告分享数" align="center" prop="shareSum" />
      <el-table-column label="小翼管家分享数" align="center" prop="yiShareSum" />
      <el-table-column label="短信分享数" align="center" prop="messageShareSum" />
      <el-table-column label="微信分享数" align="center" prop="wechatShareSum" />
      <el-table-column label="其他分享数" align="center" prop="otherShareSum" />
      <el-table-column label="有效报告匹配率" align="center" prop="effectiveReportRate" />
      <el-table-column label="交付地一致率" align="center" prop="sameAreaRate" />
      <el-table-column label="WiFi测速达标率" align="center" prop="wifiCheckedRate" />
      <el-table-column label="终端稽核率" align="center" prop="elinkCheckedRate" />
      <el-table-column label="报告分享率" align="center" prop="shareRate" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['homewifi:cityrate:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['homewifi:cityrate:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改分地市四率统计对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="省份" prop="provName">
          <el-input v-model="form.provName" placeholder="请输入省份" />
        </el-form-item>
        <el-form-item label="地市" prop="cityName">
          <el-input v-model="form.cityName" placeholder="请输入地市" />
        </el-form-item>
        <el-form-item label="有效报告数" prop="effectiveSum">
          <el-input v-model="form.effectiveSum" placeholder="请输入有效报告数" />
        </el-form-item>
        <el-form-item label="新增礼包数" prop="newGiftSum">
          <el-input v-model="form.newGiftSum" placeholder="请输入新增礼包数" />
        </el-form-item>
        <el-form-item label="新增含终端礼包数" prop="newTermiGiftSum">
          <el-input v-model="form.newTermiGiftSum" placeholder="请输入新增含终端礼包数" />
        </el-form-item>
        <el-form-item label="新增纯服务礼包数" prop="newServiGiftSum">
          <el-input v-model="form.newServiGiftSum" placeholder="请输入新增纯服务礼包数" />
        </el-form-item>
        <el-form-item label="新增e_link/e_OS终端数" prop="newElinkSum">
          <el-input v-model="form.newElinkSum" placeholder="请输入新增e_link/e_OS终端数" />
        </el-form-item>
        <el-form-item label="交付地一致数" prop="sameAreaSum">
          <el-input v-model="form.sameAreaSum" placeholder="请输入交付地一致数" />
        </el-form-item>
        <el-form-item label="测速达标数" prop="wifiCheckedSum">
          <el-input v-model="form.wifiCheckedSum" placeholder="请输入测速达标数" />
        </el-form-item>
        <el-form-item label="报告分享数" prop="shareSum">
          <el-input v-model="form.shareSum" placeholder="请输入报告分享数" />
        </el-form-item>
        <el-form-item label="小翼管家分享数" prop="yiShareSum">
          <el-input v-model="form.yiShareSum" placeholder="请输入小翼管家分享数" />
        </el-form-item>
        <el-form-item label="短信分享数" prop="messageShareSum">
          <el-input v-model="form.messageShareSum" placeholder="请输入短信分享数" />
        </el-form-item>
        <el-form-item label="微信分享数" prop="wechatShareSum">
          <el-input v-model="form.wechatShareSum" placeholder="请输入微信分享数" />
        </el-form-item>
        <el-form-item label="其他分享数" prop="otherShareSum">
          <el-input v-model="form.otherShareSum" placeholder="请输入其他分享数" />
        </el-form-item>
        <el-form-item label="有效报告匹配率" prop="effectiveReportRate">
          <el-input v-model="form.effectiveReportRate" placeholder="请输入有效报告匹配率" />
        </el-form-item>
        <el-form-item label="交付地一致率" prop="sameAreaRate">
          <el-input v-model="form.sameAreaRate" placeholder="请输入交付地一致率" />
        </el-form-item>
        <el-form-item label="WiFi测速达标率" prop="wifiCheckedRate">
          <el-input v-model="form.wifiCheckedRate" placeholder="请输入WiFi测速达标率" />
        </el-form-item>
        <el-form-item label="终端稽核率" prop="elinkCheckedRate">
          <el-input v-model="form.elinkCheckedRate" placeholder="请输入终端稽核率" />
        </el-form-item>
        <el-form-item label="报告分享率" prop="shareRate">
          <el-input v-model="form.shareRate" placeholder="请输入报告分享率" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCityrate, getCityrate, delCityrate, addCityrate, updateCityrate, exportCityrate, getCity, getProv } from "@/api/homewifi/cityrate";

export default {
  name: "Cityrate",
  data() {
    return {
      provList: [],
      cityList: [],
      // 遮罩层
      loading: false,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 分地市四率统计表格数据
      cityrateList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        provName: null,
        cityName: null,
        effectiveSum: null,
        newGiftSum: null,
        newTermiGiftSum: null,
        newServiGiftSum: null,
        newElinkSum: null,
        sameAreaSum: null,
        wifiCheckedSum: null,
        shareSum: null,
        yiShareSum: null,
        messageShareSum: null,
        wechatShareSum: null,
        otherShareSum: null,
        effectiveReportRate: null,
        sameAreaRate: null,
        wifiCheckedRate: null,
        elinkCheckedRate: null,
        shareRate: null,
        startDate: null,
        endDate: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getProv();
    // this.getList();
  },
  methods: {
    //查询省份
    getProv() {
      getProv().then(response => {
        this.provList = response;
        this.provList.sort((a, b)=> a.value.localeCompare(b.value, 'zh'));
        // this.queryParams.cityName = null
      });
    },
    //查询地市id
    getCity() {
      this.queryParams.cityName = null
      getCity(this.queryParams.provName).then(response => {
        this.cityList = response;
      });
    },
    /** 查询分地市四率统计列表 */
    getList() {
      this.loading = true;
      listCityrate(this.queryParams).then(response => {
        this.cityrateList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        provName: null,
        cityName: null,
        effectiveSum: null,
        newGiftSum: null,
        newTermiGiftSum: null,
        newServiGiftSum: null,
        newElinkSum: null,
        sameAreaSum: null,
        wifiCheckedSum: null,
        shareSum: null,
        yiShareSum: null,
        messageShareSum: null,
        wechatShareSum: null,
        otherShareSum: null,
        effectiveReportRate: null,
        sameAreaRate: null,
        wifiCheckedRate: null,
        elinkCheckedRate: null,
        shareRate: null,
        rateReserve1: null,
        rateReserve2: null,
        rateReserve3: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // this.resetForm("queryForm");
      this.queryParams.startDate= null;
      this.queryParams.endDate=null;
      this.queryParams.provName= null;
      this.queryParams.cityName=null;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加分地市四率统计";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCityrate(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改分地市四率统计";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCityrate(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCityrate(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除分地市四率统计编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCityrate(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有分地市四率统计数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportCityrate(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
