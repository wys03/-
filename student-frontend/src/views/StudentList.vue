<template>
  <div class="student-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>学生管理</span>
        </div>
      </template>

      <!-- 1. 表单组件 -->
      <el-form :inline="true" :model="queryForm" class="search-form">
        <el-form-item label="学生姓名">
          <!-- 2. v-model双向绑定 -->
          <el-input v-model="queryForm.name" placeholder="请输入学生姓名" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
          <el-button type="success" @click="handleAdd">新增学生</el-button>
        </el-form-item>
      </el-form>

      <!-- 3. 表格组件 -->
      <el-table :data="tableData" border stripe v-loading="loading">
        <!-- 表格列 -->
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="studentNo" label="学号" />
        <el-table-column prop="age" label="年龄" />
        <el-table-column prop="gender" label="性别" />
        <el-table-column prop="major" label="专业" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <Pagination
        v-model="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        @change="fetchData"
      />
    </el-card>

    <!-- 4. 对话框组件 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="学号" prop="studentNo">
          <el-input v-model="form.studentNo" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="form.age" :min="1" :max="100" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别" style="width: 100%">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="form.major" placeholder="请输入专业" />
        </el-form-item>
      </el-form>
      <template #footer>
        <!-- 5. 按钮事件绑定 -->
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getStudentList, createStudent, updateStudent, deleteStudent } from '../api/student'
import Pagination from '../components/Pagination.vue'

const loading = ref(false)
const tableData = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const queryForm = reactive({ name: '' })
const dialogVisible = ref(false)
const dialogTitle = ref('新增学生')
const formRef = ref(null)

// 5. 使用reactive创建表单对象
const form = reactive({
  id: null, name: '', studentNo: '', age: 18, gender: '男', major: ''
})

// 6. 表单验证规则
const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  studentNo: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  major: [{ required: true, message: '请输入专业', trigger: 'blur' }]
}

// 7. 获取学生列表数据
const fetchData = async () => {
  loading.value = true
  try {
    const params = { page: currentPage.value, pageSize: pageSize.value }
    if (queryForm.name) params.name = queryForm.name
    const res = await getStudentList(params)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => { currentPage.value = 1; fetchData() }
const handleReset = () => { queryForm.name = ''; currentPage.value = 1; fetchData() }

const handleAdd = () => {
  dialogTitle.value = '新增学生'
  Object.assign(form, { id: null, name: '', studentNo: '', age: 18, gender: '男', major: '' })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑学生'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该学生吗？', '提示', {
    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
  }).then(async () => {
    await deleteStudent(row.id)
    ElMessage.success('删除成功')
    fetchData()
  }).catch(() => {})
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.id) {
          await updateStudent(form)
          ElMessage.success('更新成功')
        } else {
          await createStudent(form)
          ElMessage.success('创建成功')
        }
        dialogVisible.value = false
        fetchData()
      } catch (error) {
        // 提取简化错误信息
        const errorMessage = extractErrorMessage(error)
        ElMessage.error(errorMessage)
        console.error('学生操作错误:', error)
      }
    }
  })
}

onMounted(fetchData)
</script>

<style scoped>
.student-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}
</style>
