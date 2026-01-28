<!-- 仪表板组件 -->
<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <el-statistic title="学生总数" :value="studentCount">
            <template #suffix>
              <el-icon><User /></el-icon>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <el-statistic title="课程总数" :value="courseCount">
            <template #suffix>
              <el-icon><Reading /></el-icon>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <el-statistic title="成绩记录" :value="scoreCount">
            <template #suffix>
              <el-icon><Document /></el-icon>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <el-statistic title="平均成绩" :value="avgScore" :precision="2">
            <template #suffix>分</template>
          </el-statistic>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="welcome-card">
      <template #header>
        <div class="card-header">
          <span>欢迎使用学生管理系统</span>
        </div>
      </template>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="系统版本">v1.0.0</el-descriptions-item>
        <el-descriptions-item label="技术栈">Vue 3 + Element Plus + Spring Boot</el-descriptions-item>
        <el-descriptions-item label="开发模式">前后端分离</el-descriptions-item>
        <el-descriptions-item label="当前时间">{{ currentTime }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
  import { ref, onMounted } from 'vue'
  import { User, Reading, Document } from '@element-plus/icons-vue'
  import request from '../api/request'

  // 1. 定义响应式数据
  const studentCount = ref(0)
  const courseCount = ref(0)
  const scoreCount = ref(0)
  const avgScore = ref(0)
  const currentTime = ref('')
  const loading = ref(false)

  // 2. 定义获取数据的函数
  const fetchStatistics = async () => {
    /**
     * 获取系统统计数据
     * 1. 设置加载状态为true
     * 2. 发送请求获取统计数据
     * 3. 处理成功和失败的结果
     * 4. 无论结果如何，最终设置加载状态为false
     */
    loading.value = true
    try {
      // 获取学生总数
      const studentRes = await request({ url: '/students/all', method: 'get' })
      studentCount.value = studentRes.data ? studentRes.data.length : 0

      // 获取课程总数
      const courseRes = await request({ url: '/courses/all', method: 'get' })
      courseCount.value = courseRes.data ? courseRes.data.length : 0

      // 获取所有成绩计算总数和平均分
      const scoresRes = await request({ url: '/scores', method: 'get', params: { page: 1, size: 1000 } })
      const scores = scoresRes.data?.records || []
      scoreCount.value = scoresRes.data?.total || 0

      // 计算平均分
      if (scores.length > 0) {
        const total = scores.reduce((sum, item) => sum + item.score, 0)
        avgScore.value = total / scores.length
      }
    } catch (error) {
      console.error('获取统计数据失败:', error)
    } finally {
      loading.value = false
    }
  }

  // 3. 生命周期钩子：组件挂载时执行
  onMounted(() => {
    updateCurrentTime()
    setInterval(updateCurrentTime, 1000)
    fetchStatistics()
  })

  // 4. 定义更新当前时间的函数
  const updateCurrentTime = () => {
    const now = new Date()
    currentTime.value = now.toLocaleString('zh-CN')
  }
</script>

<style scoped>
  .dashboard {
    padding: 20px;
  }

  .stat-card {
    margin-bottom: 20px;
  }

  .stat-card :deep(.el-statistic__head) {
    font-size: 16px;
    color: #606266;
  }

  .stat-card :deep(.el-statistic__number) {
    font-size: 32px;
    font-weight: bold;
    color: #409eff;
  }

  .welcome-card {
    margin-top: 20px;
  }

  .card-header {
    font-size: 18px;
    font-weight: bold;
  }
</style>
