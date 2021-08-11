<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工单号" prop="workOrderNum">
        <el-input
          v-model="queryParams.workOrderNum"
          placeholder="请输入工单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="dayId">
        <el-date-picker clearable size="small"
          v-model="queryParams.dayId"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="省份id" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入省份id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地市id" prop="cityId">
        <el-input
          v-model="queryParams.cityId"
          placeholder="请输入地市id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地市编号" prop="areaId">
        <el-input
          v-model="queryParams.areaId"
          placeholder="请输入地市编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交付地一致性校验是否成功" prop="sameArea">
        <el-input
          v-model="queryParams.sameArea"
          placeholder="请输入交付地一致性校验是否成功"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否有效竣工报告" prop="effectiveReport">
        <el-input
          v-model="queryParams.effectiveReport"
          placeholder="请输入是否有效竣工报告"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="终端稽核是否成功" prop="elinkChecked">
        <el-input
          v-model="queryParams.elinkChecked"
          placeholder="请输入终端稽核是否成功"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="WiFi测速是否达标" prop="wifiChecked">
        <el-input
          v-model="queryParams.wifiChecked"
          placeholder="请输入WiFi测速是否达标"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交付报告是否分享" prop="shareChecked">
        <el-input
          v-model="queryParams.shareChecked"
          placeholder="请输入交付报告是否分享"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交付报告分享渠道" prop="shareMethod">
        <el-input
          v-model="queryParams.shareMethod"
          placeholder="请输入交付报告分享渠道"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
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
          v-hasPermi="['homewifi:installationlisti:add']"
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
          v-hasPermi="['homewifi:installationlisti:edit']"
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
          v-hasPermi="['homewifi:installationlisti:remove']"
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
          v-hasPermi="['homewifi:installationlisti:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="installationlistiList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="工单号" align="center" prop="workOrderNum" />
      <el-table-column label="创建时间" align="center" prop="dayId" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dayId, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="省份id" align="center" prop="deptId" />
      <el-table-column label="地市id" align="center" prop="cityId" />
      <el-table-column label="地市编号" align="center" prop="areaId" />
      <el-table-column label="交付地一致性校验是否成功" align="center" prop="sameArea" />
      <el-table-column label="是否有效竣工报告" align="center" prop="effectiveReport" />
      <el-table-column label="终端稽核是否成功" align="center" prop="elinkChecked" />
      <el-table-column label="WiFi测速是否达标" align="center" prop="wifiChecked" />
      <el-table-column label="交付报告是否分享" align="center" prop="shareChecked" />
      <el-table-column label="交付报告分享渠道" align="center" prop="shareMethod" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['homewifi:installationlisti:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['homewifi:installationlisti:remove']"
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

    <!-- 添加或修改数据湖工单下发对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="工单号" prop="workOrderNum">
          <el-input v-model="form.workOrderNum" placeholder="请输入工单号" />
        </el-form-item>
        <el-form-item label="创建时间" prop="dayId">
          <el-date-picker clearable size="small"
            v-model="form.dayId"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="省份id" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入省份id" />
        </el-form-item>
        <el-form-item label="地市id" prop="cityId">
          <el-input v-model="form.cityId" placeholder="请输入地市id" />
        </el-form-item>
        <el-form-item label="地市编号" prop="areaId">
          <el-input v-model="form.areaId" placeholder="请输入地市编号" />
        </el-form-item>
        <el-form-item label="交付地一致性校验是否成功" prop="sameArea">
          <el-input v-model="form.sameArea" placeholder="请输入交付地一致性校验是否成功" />
        </el-form-item>
        <el-form-item label="是否有效竣工报告" prop="effectiveReport">
          <el-input v-model="form.effectiveReport" placeholder="请输入是否有效竣工报告" />
        </el-form-item>
        <el-form-item label="终端稽核是否成功" prop="elinkChecked">
          <el-input v-model="form.elinkChecked" placeholder="请输入终端稽核是否成功" />
        </el-form-item>
        <el-form-item label="WiFi测速是否达标" prop="wifiChecked">
          <el-input v-model="form.wifiChecked" placeholder="请输入WiFi测速是否达标" />
        </el-form-item>
        <el-form-item label="交付报告是否分享" prop="shareChecked">
          <el-input v-model="form.shareChecked" placeholder="请输入交付报告是否分享" />
        </el-form-item>
        <el-form-item label="交付报告分享渠道" prop="shareMethod">
          <el-input v-model="form.shareMethod" placeholder="请输入交付报告分享渠道" />
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
import { listInstallationlisti, getInstallationlisti, delInstallationlisti, addInstallationlisti, updateInstallationlisti, exportInstallationlisti } from "@/api/homewifi/installationlisti";

export default {
  name: "Installationlisti",
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // 数据湖工单下发表格数据
      installationlistiList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workOrderNum: null,
        dayId: null,
        deptId: null,
        cityId: null,
        areaId: null,
        sameArea: null,
        effectiveReport: null,
        elinkChecked: null,
        wifiChecked: null,
        shareChecked: null,
        shareMethod: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询数据湖工单下发列表 */
    getList() {
      this.loading = true;
      listInstallationlisti(this.queryParams).then(response => {
        this.installationlistiList = response.rows;
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
        workOrderNum: null,
        dayId: null,
        deptId: null,
        cityId: null,
        areaId: null,
        sameArea: null,
        effectiveReport: null,
        elinkChecked: null,
        wifiChecked: null,
        shareChecked: null,
        shareMethod: null
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
      this.resetForm("queryForm");
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
      this.title = "添加数据湖工单下发";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInstallationlisti(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改数据湖工单下发";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInstallationlisti(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInstallationlisti(this.form).then(response => {
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
      this.$confirm('是否确认删除数据湖工单下发编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delInstallationlisti(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有数据湖工单下发数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportInstallationlisti(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
