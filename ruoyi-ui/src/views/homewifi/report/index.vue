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
<!--      <el-form-item label="查询地区" prop="provName">-->
<!--        <el-input-->
<!--          v-model="queryParams.provName"-->
<!--          placeholder="请输入省份或地市"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
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
<!--      <el-form-item label="地市" prop="cityName">-->
<!--        <el-input-->
<!--          v-model="queryParams.cityName"-->
<!--          placeholder="请输入地市"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="县区" prop="areaName">-->
<!--        <el-input-->
<!--          v-model="queryParams.areaName"-->
<!--          placeholder="请输入县区"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="工单号" prop="orderid">
        <el-input
          v-model="queryParams.orderid"
          placeholder="请输入工单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="工单类型" prop="ordertype">-->
<!--        <el-select v-model="queryParams.ordertype" placeholder="请选择工单类型" clearable size="small">-->
<!--          <el-option-->
<!--            v-for="dict in ordertypeOptions"-->
<!--            :key="dict.dictValue"-->
<!--            :label="dict.dictLabel"-->
<!--            :value="dict.dictValue"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item label="工程师账号" prop="engineerId" label-width="100px">
        <el-input
          v-model="queryParams.engineerId"
          placeholder="请输入工程师账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="装维姓名" prop="engineerName">-->
<!--        <el-input-->
<!--          v-model="queryParams.engineerName"-->
<!--          placeholder="请输入装维姓名"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="装维手机号" prop="engineerTel">-->
<!--        <el-input-->
<!--          v-model="queryParams.engineerTel"-->
<!--          placeholder="请输入装维手机号"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="生成时间" prop="createTime">-->
<!--        <el-date-picker clearable size="small"-->
<!--          v-model="queryParams.createTime"-->
<!--          type="date"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          placeholder="选择生成时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
      <el-form-item label="宽带账号" prop="userAccount" label-width="80px">
        <el-input
          v-model="queryParams.userAccount"
          placeholder="请输入宽带账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="校验结果">
        <el-select v-model="queryParams.effectiveReport" clearable placeholder="请选择校验结果" class="handle-select mr10">
          <el-option v-for="item in resultList" :key="item.key" :label="item.value" :value="item.key"></el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item label="3A返查宽带账号" prop="aaaPppoe" label-width="120px">-->
<!--        <el-input-->
<!--          v-model="queryParams.aaaPppoe"-->
<!--          placeholder="请输入3A返查宽带账号"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="签约速率 / M" prop="clientSignedSpeed">-->
<!--        <el-input-->
<!--          v-model="queryParams.clientSignedSpeed"-->
<!--          placeholder="请输入签约速率 / M"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="终端信息" prop="terminalMacList">-->
<!--        <el-input-->
<!--          v-model="queryParams.terminalMacList"-->
<!--          placeholder="请输入终端信息"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="分享校验" prop="wwShareChecked">-->
<!--        <el-select v-model="queryParams.wwShareChecked" placeholder="请选择分享校验" clearable size="small">-->
<!--          <el-option-->
<!--            v-for="dict in wwShareCheckedOptions"-->
<!--            :key="dict.dictValue"-->
<!--            :label="dict.dictLabel"-->
<!--            :value="dict.dictValue"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="分享渠道" prop="wwShareMethod">-->
<!--        <el-select v-model="queryParams.wwShareMethod" placeholder="请选择分享渠道" clearable size="small">-->
<!--          <el-option-->
<!--            v-for="dict in wwShareMethodOptions"-->
<!--            :key="dict.dictValue"-->
<!--            :label="dict.dictLabel"-->
<!--            :value="dict.dictValue"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="分享时间" prop="wwShareTime">-->
<!--        <el-date-picker clearable size="small"-->
<!--          v-model="queryParams.wwShareTime"-->
<!--          type="date"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          placeholder="选择分享时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="交付地一致性校验" prop="sameArea">-->
<!--        <el-select v-model="queryParams.sameArea" placeholder="请选择交付地一致性校验" clearable size="small">-->
<!--          <el-option-->
<!--            v-for="dict in sameAreaOptions"-->
<!--            :key="dict.dictValue"-->
<!--            :label="dict.dictLabel"-->
<!--            :value="dict.dictValue"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="有效报告校验" prop="effectiveReport">-->
<!--        <el-select v-model="queryParams.effectiveReport" placeholder="请选择有效报告校验" clearable size="small">-->
<!--          <el-option-->
<!--            v-for="dict in effectiveReportOptions"-->
<!--            :key="dict.dictValue"-->
<!--            :label="dict.dictLabel"-->
<!--            :value="dict.dictValue"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="终端稽核校验" prop="elinkChecked">-->
<!--        <el-select v-model="queryParams.elinkChecked" placeholder="请选择终端稽核校验" clearable size="small">-->
<!--          <el-option-->
<!--            v-for="dict in elinkCheckedOptions"-->
<!--            :key="dict.dictValue"-->
<!--            :label="dict.dictLabel"-->
<!--            :value="dict.dictValue"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="WiFi测速校验" prop="wifiChecked">-->
<!--        <el-select v-model="queryParams.wifiChecked" placeholder="请选择WiFi测速校验" clearable size="small">-->
<!--          <el-option-->
<!--            v-for="dict in wifiCheckedOptions"-->
<!--            :key="dict.dictValue"-->
<!--            :label="dict.dictLabel"-->
<!--            :value="dict.dictValue"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['homewifi:report:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['homewifi:report:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['homewifi:report:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
		  :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['homewifi:report:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reportList" @selection-change="handleSelectionChange">
<!--      <el-table-column type="selection" width="55" align="center" />-->
      <el-table-column label="主键id" align="center" prop="id" v-if="showId"/>
      <el-table-column label="省份" align="center" prop="provName" />
      <el-table-column label="地市" align="center" prop="cityName" />
      <el-table-column label="县区" align="center" prop="areaName" />
      <el-table-column label="工单号" align="center" prop="orderid" />
      <el-table-column label="工单类型" align="center" prop="ordertype" :formatter="ordertypeFormat" />
      <el-table-column label="装维工号" align="center" prop="engineerId" />
      <el-table-column label="装维姓名" align="center" prop="engineerName" />
      <el-table-column label="装维手机号" align="center" prop="engineerTel" />
      <el-table-column label="生成时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="工单宽带账号" align="center" prop="userAccount" />
      <el-table-column label="3A返查宽带账号" align="center" prop="aaaPppoe" />
      <el-table-column label="签约速率 / M" align="center" prop="clientSignedSpeed" />
      <el-table-column label="终端信息" align="center" prop="terminalMacList" />
      <el-table-column label="分享校验" align="center" prop="wwShareChecked" :formatter="wwShareCheckedFormat" />
      <el-table-column label="分享渠道" align="center" prop="wwShareMethod" :formatter="wwShareMethodFormat" />
      <el-table-column label="分享时间" align="center" prop="wwShareTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.wwShareTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="交付地一致性校验" align="center" prop="sameArea" :formatter="sameAreaFormat" />
      <el-table-column label="有效报告校验" align="center" prop="effectiveReport" :formatter="effectiveReportFormat" />
      <el-table-column label="终端稽核校验" align="center" prop="elinkChecked" :formatter="elinkCheckedFormat" />
      <el-table-column label="WiFi测速校验" align="center" prop="wifiChecked" :formatter="wifiCheckedFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="showHandle">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['homewifi:report:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['homewifi:report:remove']"
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

    <!-- 添加或修改竣工报告查询对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="省份" prop="provName">
          <el-input v-model="form.provName" placeholder="请输入省份" />
        </el-form-item>
        <el-form-item label="地市" prop="cityName">
          <el-input v-model="form.cityName" placeholder="请输入地市" />
        </el-form-item>
        <el-form-item label="县区" prop="areaName">
          <el-input v-model="form.areaName" placeholder="请输入县区" />
        </el-form-item>
        <el-form-item label="工单号" prop="orderid">
          <el-input v-model="form.orderid" placeholder="请输入工单号" />
        </el-form-item>
        <el-form-item label="工单类型" prop="ordertype">
          <el-select v-model="form.ordertype" placeholder="请选择工单类型">
            <el-option
              v-for="dict in ordertypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="装维工号" prop="engineerId">
          <el-input v-model="form.engineerId" placeholder="请输入装维工号" />
        </el-form-item>
        <el-form-item label="装维姓名" prop="engineerName">
          <el-input v-model="form.engineerName" placeholder="请输入装维姓名" />
        </el-form-item>
        <el-form-item label="装维手机号" prop="engineerTel">
          <el-input v-model="form.engineerTel" placeholder="请输入装维手机号" />
        </el-form-item>
        <el-form-item label="工单宽带账号" prop="userAccount">
          <el-input v-model="form.userAccount" placeholder="请输入工单宽带账号" />
        </el-form-item>
        <el-form-item label="3A返查宽带账号" prop="aaaPppoe">
          <el-input v-model="form.aaaPppoe" placeholder="请输入3A返查宽带账号" />
        </el-form-item>
        <el-form-item label="签约速率 / M" prop="clientSignedSpeed">
          <el-input v-model="form.clientSignedSpeed" placeholder="请输入签约速率 / M" />
        </el-form-item>
        <el-form-item label="终端信息" prop="terminalMacList">
          <el-input v-model="form.terminalMacList" placeholder="请输入终端信息" />
        </el-form-item>
        <el-form-item label="分享校验" prop="wwShareChecked">
          <el-select v-model="form.wwShareChecked" placeholder="请选择分享校验">
            <el-option
              v-for="dict in wwShareCheckedOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分享渠道" prop="wwShareMethod">
          <el-select v-model="form.wwShareMethod" placeholder="请选择分享渠道">
            <el-option
              v-for="dict in wwShareMethodOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分享时间" prop="wwShareTime">
          <el-date-picker clearable size="small"
            v-model="form.wwShareTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择分享时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="交付地一致性校验" prop="sameArea">
          <el-select v-model="form.sameArea" placeholder="请选择交付地一致性校验">
            <el-option
              v-for="dict in sameAreaOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="有效报告校验" prop="effectiveReport">
          <el-select v-model="form.effectiveReport" placeholder="请选择有效报告校验">
            <el-option
              v-for="dict in effectiveReportOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="终端稽核校验" prop="elinkChecked">
          <el-select v-model="form.elinkChecked" placeholder="请选择终端稽核校验">
            <el-option
              v-for="dict in elinkCheckedOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="WiFi测速校验" prop="wifiChecked">
          <el-select v-model="form.wifiChecked" placeholder="请选择WiFi测速校验">
            <el-option
              v-for="dict in wifiCheckedOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
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
import { listReport, getReport, delReport, addReport, updateReport, exportReport, getProv, getCity } from "@/api/homewifi/report";

export default {
  name: "Report",
  data() {
    return {
      showHandle:false,
      showId:false,
      provList: [],
      cityList: [],
      options: [],
      value: '',
      //校验结果
      resultList:[
        {
          "key":"3",
          "value":"所有"
        },
        {
          "key":"0",
          "value":"不通过"
        },{
          "key":"1",
          "value":"通过"
        }
      ],
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
      // 竣工报告查询表格数据
      reportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 工单类型字典
      ordertypeOptions: [],
      // 分享校验字典
      wwShareCheckedOptions: [],
      // 分享渠道字典
      wwShareMethodOptions: [],
      // 交付地一致性校验字典
      sameAreaOptions: [],
      // 有效报告校验字典
      effectiveReportOptions: [],
      // 终端稽核校验字典
      elinkCheckedOptions: [],
      // WiFi测速校验字典
      wifiCheckedOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        provName: null,
        cityName: null,
        areaName: null,
        orderid: null,
        ordertype: null,
        engineerId: null,
        engineerName: null,
        engineerTel: null,
        createTime: null,
        userAccount: null,
        aaaPppoe: null,
        clientSignedSpeed: null,
        terminalMacList: null,
        wwShareChecked: null,
        wwShareMethod: null,
        wwShareTime: null,
        sameArea: null,
        effectiveReport: null,
        elinkChecked: null,
        wifiChecked: null,
        //日期选择
        startDate: null,
        endDate: null,
        // provId: null,
        // cityId: null
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
    this.getList();
    this.getDicts("data_order_type").then(response => {
      this.ordertypeOptions = response.data;
    });
    this.getDicts("data_check_type").then(response => {
      this.wwShareCheckedOptions = response.data;
    });
    this.getDicts("data_share_type").then(response => {
      this.wwShareMethodOptions = response.data;
    });
    this.getDicts("data_check_type").then(response => {
      this.sameAreaOptions = response.data;
    });
    this.getDicts("data_check_type").then(response => {
      this.effectiveReportOptions = response.data;
    });
    this.getDicts("data_check_type").then(response => {
      this.elinkCheckedOptions = response.data;
    });
    this.getDicts("data_check_type").then(response => {
      this.wifiCheckedOptions = response.data;
    });
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
    // //选择校验结果
    // changeResult(event){
    //   this.queryParams.result = event.key;
    // },
    /** 查询竣工报告查询列表 */
    getList() {
      this.loading = true;
      listReport(this.queryParams).then(response => {
        this.reportList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 工单类型字典翻译
    ordertypeFormat(row, column) {
      return this.selectDictLabel(this.ordertypeOptions, row.ordertype);
    },
    // 分享校验字典翻译
    wwShareCheckedFormat(row, column) {
      return this.selectDictLabel(this.wwShareCheckedOptions, row.wwShareChecked);
    },
    // 分享渠道字典翻译
    wwShareMethodFormat(row, column) {
      return this.selectDictLabel(this.wwShareMethodOptions, row.wwShareMethod);
    },
    // 交付地一致性校验字典翻译
    sameAreaFormat(row, column) {
      return this.selectDictLabel(this.sameAreaOptions, row.sameArea);
    },
    // 有效报告校验字典翻译
    effectiveReportFormat(row, column) {
      return this.selectDictLabel(this.effectiveReportOptions, row.effectiveReport);
    },
    // 终端稽核校验字典翻译
    elinkCheckedFormat(row, column) {
      return this.selectDictLabel(this.elinkCheckedOptions, row.elinkChecked);
    },
    // WiFi测速校验字典翻译
    wifiCheckedFormat(row, column) {
      return this.selectDictLabel(this.wifiCheckedOptions, row.wifiChecked);
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
        areaName: null,
        orderid: null,
        ordertype: null,
        engineerId: null,
        engineerName: null,
        engineerTel: null,
        createTime: null,
        userAccount: null,
        aaaPppoe: null,
        clientSignedSpeed: null,
        terminalMacList: null,
        wwShareChecked: null,
        wwShareMethod: null,
        wwShareTime: null,
        sameArea: null,
        effectiveReport: null,
        elinkChecked: null,
        wifiChecked: null,
        startDate: null,
        endDate: null
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
      this.queryParams.orderid= null;
      this.queryParams.engineerId=null;
      this.queryParams.userAccount= null;
      this.queryParams.effectiveReport=null;
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
      this.title = "添加竣工报告查询";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getReport(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改竣工报告查询";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReport(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReport(this.form).then(response => {
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
      this.$confirm('是否确认删除竣工报告查询编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delReport(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有竣工报告查询数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportReport(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
