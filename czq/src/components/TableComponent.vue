<template>
  <div class="edit_head_div">
    <el-button class="create_btn" @click="createUser">新建</el-button>
    <el-input class="search_input" placeholder="输入姓名搜索" style="width: 180px" v-model="searchValue" />
    <el-button class="search_btn" @click="doSearch">查询</el-button>
  </div>
  <el-table :data="tableData" height="700" style="width: 100%" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" />
    <el-table-column prop="name" label="姓名" width="280" />
    <el-table-column prop="age" label="年龄" width="80" />
    <el-table-column prop="gender" label="性别" width="80" />
    <el-table-column prop="phone" label="联系电话" width="300" />
    <el-table-column prop="address" label="详细地址" width="900"/>
    <el-table-column>
      <template #default="scope">
        <el-button link type="primary" size="small" @click="editUser(scope.row.id)">编辑</el-button>
        <el-button link type="danger" size="small" @click="delUser([scope.row.id])">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-button class="del_btn" @click="deleteMultiple">批量删除</el-button>
  <div class="demo_pagination_block">
    <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 30, 40]"
        layout="total, sizes, prev, pager, next"
        :total="totalPage"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
    />
  </div>
  <form-component v-if="showEditWindow" @changeShowEditWindow="changeShowEditWindow" :user="user" @refresh="doSearch"/>
  <AlterComponent v-if="showWarringWindow" @changeShowWarringWindow="changeShowWarringWindow" :deleteData="deleteData"/>
</template>

<script setup>
import {get, del} from '@/util/http_util'
import {onMounted, reactive, ref} from "vue"
import areaData from '../data/area_data.json'
import FormComponent from "@/components/FormComponent.vue";
import {getPageData, setPageData} from "@/util/local_storage_util";
import AlterComponent from "@/components/DeleteAlterComponent.vue";

const tableData = reactive([])
const userData = ref({})
const user = ref({})
const selectedRows = ref([])
const showEditWindow = ref(false)
const showWarringWindow = ref(false)
const searchValue = ref('')
const pageSize = ref(10)
const currentPage = ref(1)
const totalPage = ref(400)
const deleteData = ref('')

onMounted(function () {
  let pageData = getPageData();  // 获取localStorage数据
  searchValue.value = pageData.searchValue
  pageSize.value = parseInt(pageData.pageSize)
  currentPage.value = parseInt(pageData.pageNum)
  getUsers({name: searchValue.value, size: pageSize.value, pageNum: currentPage.value})
});

function handleSizeChange(size) {
  setPageData(size, currentPage.value, searchValue.value)
  doSearch()
}

function handleCurrentChange(currentPage) {
  setPageData(pageSize.value, currentPage, searchValue.value)
  doSearch()
}

function doSearch() {
  getUsers({name: searchValue.value, size: pageSize.value, pageNum: currentPage.value})
}

function deleteMultiple() {
  delUser(selectedRows.value)
}

function getUsers(data) {  // 获取table数据
  get('user', {params: data}, rep => {
    tableData.length = 0
    userData.value = rep.data.data
    totalPage.value = rep.data.total
    setPageData(rep.data.size, rep.data.currentPage, searchValue.value)
    changeToTableData(rep.data.data)
  });
}

function createUser() {
  user.value = {
    id: '',
    name: '',
    age: '0',
    gender: '1',
    phone: '',
    detailedAddress: '',
    address: '',
  }
  showEditWindow.value = true
}

function changeShowEditWindow(val) {
  showEditWindow.value = val
}

function changeShowWarringWindow(val) {
  showWarringWindow.value = val
  doSearch()
}

function handleSelectionChange(e) {  // 选择器监听
  selectedRows.value.length = 0
  for (let i = 0; i < e.length; i++) {
    selectedRows.value.push(e[i].id)
  }
}

function changeToTableData(array) {  // 数据格式转换
  for (let i = 0; i < array.length; i++) {
    console.log(array[i].address)
    let arrayElement = array[i];
    tableData.push({
      id: arrayElement.id,
      name: arrayElement.name,
      age: arrayElement.age,
      gender: arrayElement.gender === 0 ? '女' : '男',
      phone: arrayElement.phone,
      address: getAddressText(arrayElement.detailedAddress, arrayElement.address.split(','))
    })
  }
}


function getAddressText(detailedAddress, addressArray) {  // 地址数据转文本
  let index = '86';
  let text = '';
  for (let addressArrayKey in addressArray) {
    let arrayElement = addressArray[addressArrayKey];
    text += areaData[index][arrayElement.toString()] + '/'
    index = arrayElement.toString()
  }
  text += detailedAddress
  return text
}

function editUser(d) {
  for (let valueKey in userData.value) {
    if (userData.value[valueKey].id == d) {
      user.value = userData.value[valueKey]
      console.log(user.value);
      showEditWindow.value = true
    }
  }
}

function delUser(d) {
  console.log('d:' + d)
  let str = ''
  for (let i = 0; i < d.length; i++) {
    console.log('sdfsdaf' + d[i])
    str += d[i] + ','
  }
  console.log(str)
  deleteData.value = str;
  showWarringWindow.value = true
}



</script>

<style scoped>
.create_btn {
  margin-top: 16px;
  margin-left: 10px;
}

.search_input {
  margin-top: 15px;
  margin-left: 20px;
}

.search_btn {
  margin-top: 15px;
  margin-left: 5px;
}

.del_btn {
  margin-top: 15px;
  margin-left: 15px;
}

.edit_head_div {
  height: 60px;
  width: 100%;
  /*background-color: aquamarine;*/
}
.demo_pagination_block {
  margin-top: -30px;
  margin-left: 1300px;
}

</style>