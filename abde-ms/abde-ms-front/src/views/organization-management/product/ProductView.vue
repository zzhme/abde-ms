<template>
  <div>
    <el-container style="min-height: 560px; border: 1px solid #eee">
      <el-header style="display: flex; justify-content: space-between;">
        <span style="font-size: 40px;font-family: '微软雅黑'; ">账户-品牌-部门-员工智能辅助系统</span>
        <el-button type="primary" icon="el-icon-switch-button" @click="handleLogout()">退出登录</el-button>
      </el-header>
      <el-container>
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <el-menu :default-openeds="['1']">
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-message"></i>系统信息管理</template>
              <el-menu-item-group>
                <el-menu-item index="1-1"><router-link to="/account">账户管理</router-link></el-menu-item>
                <el-menu-item index="1-2"><router-link to="/brand">品牌管理</router-link></el-menu-item>
                <el-menu-item index="1-3"><router-link to="/dept">部门管理</router-link></el-menu-item>
                <el-menu-item index="1-4"><router-link to="/emp">员工管理</router-link></el-menu-item>
                <el-menu-item index="1-5"><router-link to="/product">产品管理</router-link></el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <el-form :inline="true" :model="queryFormData" class="demo-form-inline">
            <el-form-item label="产品名称">
              <el-input v-model="queryFormData.name" clearable placeholder="模糊查询"></el-input>
            </el-form-item>
            <el-form-item label="最低价">
              <el-input v-model="queryFormData.minPrice" clearable placeholder="含" style="width:120px"></el-input>
            </el-form-item>
            <el-form-item label="最高价">
              <el-input v-model="queryFormData.maxPrice" clearable placeholder="含" style="width:120px"></el-input>
            </el-form-item>
            <el-form-item label="状态">
              <el-select v-model="queryFormData.status" clearable placeholder="全部" style="width:120px">
                <el-option label="上架" :value="1"></el-option>
                <el-option label="下架" :value="0"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSearch">查询</el-button>
              <el-button @click="handleReset">重置</el-button>
            </el-form-item>
          </el-form>

          <el-row>
            <el-button type="primary" @click="showAddDialog">+&nbsp;&nbsp;&nbsp;新增产品</el-button>
            <el-button type="danger" plain :disabled="!multipleSelection.length" @click="batchDelete">批量删除</el-button>
          </el-row>
          <br>

          <el-dialog title="新增产品" :visible.sync="addDialogVisible" width="36%">
            <el-form ref="form" :model="addFormData" label-width="100px">
              <el-form-item label="产品名称">
                <el-input v-model="addFormData.name" placeholder="请输入产品名称"></el-input>
              </el-form-item>
              <el-form-item label="产品简介">
                <el-input type="textarea" v-model="addFormData.description" placeholder="选填"></el-input>
              </el-form-item>
              <el-form-item label="封面图">
                <el-upload
                    name="uploadFile"
                    class="avatar-uploader"
                    action="http://localhost:8080/commons/upload"
                    :headers="uploadHeaders"
                    :show-file-list="false"
                    :on-success="handleAddCoverSuccess"
                    :before-upload="beforeCoverUpload">
                  <img v-if="addFormData.coverUrl" :src="addFormData.coverUrl" class="avatar" alt="">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                <span style="color:#909399;font-size:12px;">点击上传，走已有 OSS 接口（与品牌/员工一致）</span>
              </el-form-item>
              <el-form-item label="单价">
                <el-input v-model="addFormData.price" placeholder="例如 99.00"></el-input>
              </el-form-item>
              <el-form-item label="状态">
                <el-switch v-model="addFormData.status" :active-value="1" :inactive-value="0"
                           active-text="上架" inactive-text="下架"></el-switch>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="add">保 存</el-button>
                <el-button @click="addDialogVisible = false">取 消</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>

          <el-dialog title="编辑产品" :visible.sync="updateDialogVisible" width="36%">
            <el-form ref="form2" :model="updateFormData" label-width="100px">
              <el-form-item label="产品名称">
                <el-input v-model="updateFormData.name"></el-input>
              </el-form-item>
              <el-form-item label="产品简介">
                <el-input type="textarea" v-model="updateFormData.description"></el-input>
              </el-form-item>
              <el-form-item label="封面图">
                <el-upload
                    name="uploadFile"
                    class="avatar-uploader"
                    action="http://localhost:8080/commons/upload"
                    :headers="uploadHeaders"
                    :show-file-list="false"
                    :on-success="handleUpdateCoverSuccess"
                    :before-upload="beforeCoverUpload">
                  <img v-if="updateFormData.coverUrl" :src="updateFormData.coverUrl" class="avatar" alt="">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </el-form-item>
              <el-form-item label="单价">
                <el-input v-model="updateFormData.price"></el-input>
              </el-form-item>
              <el-form-item label="状态">
                <el-switch v-model="updateFormData.status" :active-value="1" :inactive-value="0"
                           active-text="上架" inactive-text="下架"></el-switch>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="update">保 存</el-button>
                <el-button @click="updateDialogVisible = false">取 消</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>

          <el-table
              :data="tableData"
              border
              style="width: 100%"
              @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column label="序号" align="center" width="70">
              <template slot-scope="scope">
                {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column label="封面" align="center" width="120">
              <template slot-scope="scope">
                <el-image
                    v-if="scope.row.coverUrl"
                    style="width: 72px; height: 72px"
                    :src="scope.row.coverUrl"
                    fit="cover"></el-image>
                <span v-else>—</span>
              </template>
            </el-table-column>
            <el-table-column prop="name" align="center" label="产品名称" min-width="120"></el-table-column>
            <el-table-column prop="description" align="center" label="简介" min-width="160"
                             show-overflow-tooltip></el-table-column>
            <el-table-column prop="price" align="center" label="单价" width="100"></el-table-column>
            <el-table-column align="center" label="状态" width="90">
              <template slot-scope="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
                  {{ scope.row.status === 1 ? '上架' : '下架' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="updateTime" align="center" label="最后操作时间" width="170"></el-table-column>
            <el-table-column align="center" label="操作" width="200">
              <template slot-scope="scope">
                <el-button type="primary" plain @click="showUpdateDialog(scope.row)">编辑</el-button>
                <el-button type="danger" plain @click="deleteById(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-pagination
              background
              style="margin-top: 16px"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="pageSizes"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="totalCount">
          </el-pagination>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      queryFormData: {
        name: '',
        minPrice: '',
        maxPrice: '',
        status: null
      },
      currentPage: 1,
      pageSize: 10,
      totalCount: 0,
      pageSizes: [5, 10, 15, 20],
      addDialogVisible: false,
      updateDialogVisible: false,
      addFormData: {
        name: '',
        description: '',
        coverUrl: '',
        price: '',
        status: 1
      },
      updateFormData: {
        id: null,
        name: '',
        description: '',
        coverUrl: '',
        price: '',
        status: 1
      },
      tableData: [],
      multipleSelection: []
    };
  },
  computed: {
    uploadHeaders() {
      const token = sessionStorage.getItem('ade_token');
      return token ? { token } : {};
    }
  },
  mounted() {
    this.queryPageByCondition();
  },
  methods: {
    buildQueryBody() {
      const minP = this.queryFormData.minPrice;
      const maxP = this.queryFormData.maxPrice;
      let minPrice = minP === '' || minP === null || minP === undefined ? null : Number(minP);
      let maxPrice = maxP === '' || maxP === null || maxP === undefined ? null : Number(maxP);
      if (Number.isNaN(minPrice)) {
        minPrice = null;
      }
      if (Number.isNaN(maxPrice)) {
        maxPrice = null;
      }
      const st = this.queryFormData.status;
      const status = st === '' || st === null || st === undefined ? null : Number(st);
      const nm = this.queryFormData.name;
      return {
        name: nm && nm.trim() ? nm.trim() : null,
        minPrice,
        maxPrice,
        status: Number.isNaN(status) ? null : status,
        currentPage: this.currentPage,
        pageSize: this.pageSize
      };
    },
    queryPageByCondition() {
      const body = this.buildQueryBody();
      this.$http({ method: 'post', url: '/products', data: body }).then(resp => {
        if (resp.data.code == '1') {
          const page = resp.data.data || {};
          this.totalCount = page.totalCount || 0;
          this.tableData = page.dataList || [];
          this.multipleSelection = [];
        }
      });
    },
    handleSearch() {
      this.currentPage = 1;
      this.queryPageByCondition();
    },
    handleReset() {
      this.queryFormData = {
        name: '',
        minPrice: '',
        maxPrice: '',
        status: null
      };
      this.currentPage = 1;
      this.pageSize = 10;
      this.queryPageByCondition();
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.queryPageByCondition();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.queryPageByCondition();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    batchDelete() {
      if (!this.multipleSelection.length) {
        this.$message.warning('请先勾选要删除的产品');
        return;
      }
      const ids = this.multipleSelection.map(row => row.id);
      this.$confirm(`确定删除选中的 ${ids.length} 条产品吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({ method: 'delete', url: '/products/batch-delete', data: ids }).then(resp => {
          if (resp.data.code == '1') {
            this.$message.success(resp.data.msg);
            this.queryPageByCondition();
          } else {
            this.$message.error(resp.data.msg);
          }
        });
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    },
    showAddDialog() {
      this.addFormData = { name: '', description: '', coverUrl: '', price: '', status: 1 };
      this.addDialogVisible = true;
    },
    showUpdateDialog(row) {
      this.$http({ method: 'get', url: '/products/' + row.id }).then(resp => {
        if (resp.data.code == '1') {
          const p = resp.data.data || {};
          this.updateFormData = {
            id: p.id,
            name: p.name,
            description: p.description || '',
            coverUrl: p.coverUrl || '',
            price: p.price != null ? String(p.price) : '',
            status: p.status != null ? p.status : 1
          };
          this.updateDialogVisible = true;
        }
      });
    },
    add() {
      const payload = {
        name: this.addFormData.name,
        description: this.addFormData.description || null,
        coverUrl: this.addFormData.coverUrl || null,
        price: this.addFormData.price === '' ? null : Number(this.addFormData.price),
        status: this.addFormData.status
      };
      this.$http({ method: 'post', url: '/products/save', data: payload }).then(resp => {
        if (resp.data.code == '1') {
          this.$message.success(resp.data.msg);
          this.addDialogVisible = false;
          this.currentPage = 1;
          this.queryPageByCondition();
        } else {
          this.$message.error(resp.data.msg);
        }
      });
    },
    update() {
      const payload = {
        id: this.updateFormData.id,
        name: this.updateFormData.name,
        description: this.updateFormData.description || null,
        coverUrl: this.updateFormData.coverUrl || null,
        price: this.updateFormData.price === '' ? null : Number(this.updateFormData.price),
        status: this.updateFormData.status
      };
      this.$http({ method: 'put', url: '/products', data: payload }).then(resp => {
        if (resp.data.code == '1') {
          this.$message.success(resp.data.msg);
          this.updateDialogVisible = false;
          this.queryPageByCondition();
        } else {
          this.$message.error(resp.data.msg);
        }
      });
    },
    deleteById(id) {
      this.$confirm('确定删除该产品吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({ method: 'delete', url: '/products/' + id }).then(resp => {
          if (resp.data.code == '1') {
            this.$message.success(resp.data.msg);
          } else {
            this.$message.error(resp.data.msg);
          }
          this.queryPageByCondition();
        });
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    },
    handleAddCoverSuccess(res) {
      if (res.code == '1') {
        this.addFormData.coverUrl = res.data;
        this.$message.success(res.msg || '上传成功');
      } else {
        this.$message.error(res.msg || '上传失败');
      }
    },
    handleUpdateCoverSuccess(res) {
      if (res.code == '1') {
        this.updateFormData.coverUrl = res.data;
        this.$message.success(res.msg || '上传成功');
      } else {
        this.$message.error(res.msg || '上传失败');
      }
    },
    beforeCoverUpload(file) {
      const okType = file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/gif' || file.type === 'image/webp';
      const okSize = file.size / 1024 / 1024 < 5;
      if (!okType) {
        this.$message.error('仅支持 JPG / PNG / GIF / WEBP');
      }
      if (!okSize) {
        this.$message.error('图片大小不能超过 5MB');
      }
      return okType && okSize;
    },
    handleLogout() {
      this.$confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        sessionStorage.removeItem('ade_token');
        this.$router.push('/');
        window.location.reload();
        this.$message.success('退出登录成功！');
      }).catch(() => {
        this.$message.info('已取消退出登录');
      });
    }
  }
};
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

::v-deep(.avatar-uploader .el-upload) {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

::v-deep(.avatar-uploader .el-upload:hover) {
  border-color: #409EFF;
}

::v-deep(.avatar-uploader-icon) {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
}
</style>
