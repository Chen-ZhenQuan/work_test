<template>
  <div class="m-7">
    <el-cascader v-model="value" :options="options" @change="handleChange" />
  </div>
</template>

<script setup>
import {onMounted, reactive, ref, toRefs} from 'vue'
import areaData from '../data/area_data.json'

const value = ref()
const options = reactive([])
const props = defineProps(['optionsData'])
const emit = defineEmits(['addressValue'])

onMounted(() => {  // 加载地址选项数据
  value.value = props.optionsData  // 接受父页面传值
  for (let areaDataKey in areaData['86']) {
    options.push({  // 导入地址json中数据
      value: areaDataKey,
      label: areaData['86'][areaDataKey],
      children: geta(areaData[areaDataKey])
    })
  }
})

function geta(json) {  //解析市级地址
  let a = []
  for (let areaDataKey in json) {
    a.push({
      value: areaDataKey,
      label: json[areaDataKey],
      children: getb(areaData[areaDataKey])
    })
  }
  return a
}

function getb(json) {  // 解析区级地址
  let a = []
  for (let areaDataKey in json) {
    a.push({
      value: areaDataKey,
      label: json[areaDataKey],
    })
  }
  return a
}

const handleChange = (v) => {  // 监听变化
  let str = '';
  for (let i = 0; i < v.length; i++) {
    str += v[i] + ','
  }
  str = str.substring(0, str.length - 1)
  emit('addressValue', str)
}


</script>


<style scoped>

</style>