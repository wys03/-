<template>
  <div class="score-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>成绩管理</span>
        </div>
      </template>

      <el-form :inline="true" :model="queryForm" class="search-form">
        <el-form-item label="学号">
          <el-input v-model="queryForm.studentNo" placeholder="请输入学号" clearable />
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input v-model="queryForm.courseName" placeholder="请输入课程名称" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
          <el-button type="success" @click="handleAdd">新增成绩</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="studentNo" label="学号" width="120" />
        <el-table-column prop="studentName" label="学生姓名" />
        <el-table-column prop="courseName" label="课程名称" />
        <el-table-column prop="score" label="成绩" width="100" />
        <el-table-column label="等级" width="100">
          <template #default="{ row }">
            <el-tag :type="getScoreType(row.score)">{{ getScoreLevel(row.score) }}</el-tag>
          </template>
        </el-table-column>
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="学号" prop="studentNo">
          <el-input
            v-model="form.studentNo"
            placeholder="请输入学生学号"
            clearable
            @blur="validateStudentNo"
          />
          <div v-if="currentStudent" class="student-info">
            <span class="info-tag">{{ currentStudent.name }}</span>
            <span class="info-tag">{{ currentStudent.gender === '男' ? '男' : '女' }}</span>
            <span class="info-tag">{{ currentStudent.className }}</span>
          </div>
        </el-form-item>
        <el-form-item label="课程" prop="courseId">
          <el-select v-model="form.courseId" placeholder="请选择课程" style="width: 100%" :disabled="!form.studentId">
            <el-option
              v-for="course in courseOptions"
              :key="course.id"
              :label="course.courseName"
              :value="course.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="成绩" prop="score">
          <el-input-number v-model="form.score" :min="0" :max="100" :disabled="!form.studentId" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getScoreList, createScore, updateScore, deleteScore } from '../api/score'
import { getStudentByStudentNo } from '../api/student'
import request from '../api/request'
import Pagination from '../components/Pagination.vue'

const loading = ref(false)
const tableData = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const courseOptions = ref([])
const currentStudent = ref(null)

const queryForm = reactive({
  studentNo: '',
  courseName: ''
})

const dialogVisible = ref(false)
const dialogTitle = ref('新增成绩')
const formRef = ref(null)

const form = reactive({
  id: null,
  studentNo: '',
  studentId: null,
  courseId: null,
  score: 0
})

const rules = {
  studentNo: [{ required: true, message: '请输入学生学号', trigger: 'blur' }],
  courseId: [{ required: true, message: '请选择课程', trigger: 'change' }],
  score: [{ required: true, message: '请输入成绩', trigger: 'blur' }]
}

const validateStudentNo = async () => {
  if (!form.studentNo || form.studentNo.trim() === '') {
    currentStudent.value = null
    form.studentId = null
    return
  }

  try {
    const res = await getStudentByStudentNo(form.studentNo.trim())
    if (res.data) {
      currentStudent.value = res.data
      form.studentId = res.data.id
      ElMessage.success(`找到学生：${res.data.name}(${res.data.studentNo})`)
    } else {
      currentStudent.value = null
      form.studentId = null
      ElMessage.warning('未找到该学号对应的学生')
    }
  } catch (error) {
    console.error(error)
    currentStudent.value = null
    form.studentId = null
    ElMessage.error('查询学生失败')
  }
}

const fetchOptions = async () => {
  try {
    const courseRes = await request({ url: '/courses/all', method: 'get' })
    courseOptions.value = courseRes.data || []
  } catch (error) {
    console.error(error)
  }
}

const fetchData = async () => {
  loading.value = true
  try {
    const params = {
      page: currentPage.value,
      pageSize: pageSize.value,
      ...queryForm
    }
    const res = await getScoreList(params)
    console.log('成绩列表数据:', res)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const getScoreLevel = (score) => {
  if (score >= 90) return '优秀'
  if (score >= 80) return '良好'
  if (score >= 70) return '中等'
  if (score >= 60) return '及格'
  return '不及格'
}

const getScoreType = (score) => {
  if (score >= 90) return 'success'  // 优秀：绿色
  if (score >= 80) return 'primary'  // 良好：主题色（改为有效值）
  if (score >= 70) return 'warning'  // 中等：橙色
  if (score >= 60) return 'info'     // 及格：蓝色
  return 'danger'                    // 不及格：红色
}

const handleSearch = () => {
  currentPage.value = 1
  fetchData()
}

const handleReset = () => {
  queryForm.studentNo = ''
  queryForm.courseName = ''
  currentPage.value = 1
  fetchData()
}

const handleAdd = () => {
  dialogTitle.value = '新增成绩'
  Object.assign(form, { id: null, studentNo: '', studentId: null, courseId: null, score: 0 })
  currentStudent.value = null
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑成绩'
  Object.assign(form, { ...row, studentNo: row.studentNo || '' })
  currentStudent.value = row.studentNo ? { name: row.studentName, studentNo: row.studentNo } : null
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该成绩吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteScore(row.id)
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
          await updateScore(form)
          ElMessage.success('更新成功')
        } else {
          await createScore(form)
          ElMessage.success('创建成功')
        }
        dialogVisible.value = false
        fetchData()
      } catch (error) {
        console.error(error)
      }
    }
  })
}

onMounted(() => {
  fetchOptions()
  fetchData()
})
</script>

<style scoped>
.score-list {
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

.student-info {
  margin-top: 8px;
  display: flex;
  gap: 8px;
}

.info-tag {
  padding: 2px 8px;
  background-color: #e6f7ff;
  border: 1px solid #91d5ff;
  border-radius: 4px;
  font-size: 12px;
  color: #1890ff;
}
</style>
