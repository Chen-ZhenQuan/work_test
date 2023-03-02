<template>
  <div class="mask">
    <div class="windows">
      <div class="text"><span>确定进行删除吗？</span></div>
      <div class="btn">
        <el-button type="danger" class="yes" @click="confirmDelete">确定</el-button>
        <el-button type="primary" class="no" @click="cancelTheOperation">取消</el-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {del} from "@/util/http_util";
const props = defineProps(['deleteData'])
const emit = defineEmits(['changeShowWarringWindow'])
const closeWindow = () => {  //通知父组件关闭
  emit('changeShowWarringWindow', false)
}

const cancelTheOperation = () => {  // 取消删除操作
  closeWindow()
}

const confirmDelete = () => {  // 确认删除，执行删除操作
  del('user', {params: {ids: props.deleteData}}, res => {
    if (res.data.status == 0) {
      // getUsers({name: searchValue.value, size: pageSize.value, pageNum: currentPage.value})
      closeWindow()
    }
  });
}
</script>

<style scoped>
/* 创建遮罩层 */
.mask{
  z-index: 99;
  position: absolute;
  background-color: rgba(100, 100, 100, 0.5);
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
}
.windows {
  width: 400px;
  height: 250px;
  background-color: #ffffff;
  border-radius: 10px;
  top: 40%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  position: absolute;
}
.text {
  position: absolute;
  background-color: #ffffff;
  border-radius: 10px;
  top: 40%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  font-size: 20px;
}
.btn {
  position: absolute;
  top: 80%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}
</style>