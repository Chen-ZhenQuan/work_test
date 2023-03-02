<template>
  <div class="mask">
    <div class="form_div">
      <div class="title">
        新建/编辑用户
      </div>
      <el-form :model="formData" :rules="rules" ref="userForm">
        <el-row>
          <el-col :span="10">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="formData.name" />
            </el-form-item>
          </el-col>
          <el-col :span="2"></el-col>
          <el-col :span="8">
            <el-form-item label="年龄">
              <el-input-number min="0" max="150" v-model="formData.age"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="15">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="formData.phone"/>
            </el-form-item>
          </el-col>
          <el-col :span="2"></el-col>
          <el-col :span="6" >
            <el-form-item label="性别">
              <el-radio-group v-model="formData.gender" class="ml-4">
                <el-radio label="1">男</el-radio>
                <el-radio label="0">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="详细地址" prop="addressArray">
              <Address :optionsData="formData.addressArray" @addressValue="addressValueChange"/>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item prop="detailedAddress" label="详细地址">
              <el-input v-model="formData.detailedAddress"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="button_div">
        <el-row>
          <el-col :span="20"></el-col>
          <el-col :span="2">
            <el-button type="primary" @click="update(userForm)">确定</el-button>
          </el-col>
          <el-col :span="2">
            <el-button @click="closeEditWindow">取消</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script setup>
import {onBeforeMount, reactive, ref} from "vue";
import Address from "@/components/AddressComponent.vue";
import {post, put} from "@/util/http_util";
import {ElMessage} from "element-plus";

const props = defineProps(["user", "refresh"])
const emit = defineEmits(['changeShowEditWindow'])
const addressValue = ref()
const userForm = ref(null)
const validArea = (rule, value, callback) => {  // 自定义表单校验规则
  console.log(formData.addressArray)
  if (formData.id == '') {
    if (addressValue.value.length <= 1) {
      callback(new Error('请选择地区'))
    }
  } else {
    if (formData.addressArray <= 1) {
      callback(new Error('请选择地区'))
    }
  }

  callback()
}
const rules = ref({
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 1, max: 25, message: '姓名长度需在1到25个字符以内', trigger: 'blur' },
  ],
  phone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3|5|7|8|9]\d{9}$/, message: '请输入正确号码格式', trigger: 'blur' },
  ],
  addressArray: [{ required: true,  validator: validArea, tirgger: 'blur' },
    { type: 'array', message: '请选择地区' }],
  detailedAddress: [
    { required: true, message: '请输入详细地址', trigger: 'blur' },
    { min: 1, max: 100, message: '详细地址长度不能超过100个字符', trigger: 'blur' }
  ]
})

const formData = reactive({
  id: Number,
  name: String,
  age: Number,
  gender: String,
  phone: Number,
  addressArray: [],
  detailedAddress: String
});

onBeforeMount(() => {
  formData.id = props.user.id
  formData.name = props.user.name
  formData.age = props.user.age
  formData.gender = props.user.gender.toString()
  formData.phone = props.user.phone
  formData.detailedAddress = props.user.detailedAddress
  formData.addressArray = props.user.address.split(',')
})

const refresh = () => {  // 通知父组件刷新
  emit('refresh')
}

const closeEditWindow = () => {  // 通知父组件关闭编辑窗口
  emit('changeShowEditWindow', false)
}

const create = () => {  // 新建用户
  post('user', {
    age: formData.age,
    name: formData.name,
    gender: formData.gender,
    phone: formData.phone,
    detailedAddress: formData.detailedAddress,
    address: addressValue.value
  }, res => {
    if (res.data.status === 0) {
      closeEditWindow()
      refresh()
      ElMessage({
        message: '新建成功',
        type: 'success',
      })
    }
  })
}

const update = (form) => {  // 更新或新建用户
  form.validate((valid) => {
    if (valid) {  // 表单验证通过
      if (formData.id == '') {
        create()  // 切换为插入
        return
      }
      put('user', {  // 更新
        id: formData.id,
        age: formData.age,
        name: formData.name,
        gender: formData.gender,
        phone: formData.phone,
        detailedAddress: formData.detailedAddress,
        address: addressValue.value
      }, res => {
        if (res.data.status === 0) {  // 完成关闭出口，刷新table
          closeEditWindow()
          refresh()
          ElMessage({
            message: '更新成功',
            type: 'success',
          })
        }
      })
    } else {
      console.log('表单验证不通过')
    }
  })

}

const addressValueChange = (v) => {
  addressValue.value = v
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


.form_div {
  position: absolute;
  width: 800px;
  height: 300px;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  text-align: right;
}

.form_div .title {
  height: 40px;
  width: 100%;
  font-size: 20px;
  text-align: center;
}

.button_div {
  margin-top: 90px;
}
</style>