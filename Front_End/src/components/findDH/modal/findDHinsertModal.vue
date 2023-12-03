<template>
  <div class="modal" tabindex="-1" id="findDHInsertModal">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">동행을 찾아보세요</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
        <div class="mb-3">
            <input v-model="title" type="text" class="form-control" placeholder="제목" />
          </div>
          <div class="mb-3">
            <!-- <div id=divEditorInsert></div> -->
            <h5 class="mt-2=3 mb-3">내용</h5>
            <ckeditor
              :editor="editor"
              v-model="editorData"
              :config="editorConfig"
              style="height: 400px"
            ></ckeditor>
          </div>
          <!-- 파일 추가 start -->

          <!-- 파일 추가 end -->
        </div>
        <div class="modal-footer">
          <button
            @click="fdhInsert"
            class="btn btn-sm btn-primary btn-outline"
            data-dismiss="modal"
            type="button"
          >
            등록
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, reactive } from 'vue'
import http from '@/common/axios.js'

import CKEditor from '@ckeditor/ckeditor5-vue'
import ClassicEditor from '@ckeditor/ckeditor5-build-classic'


const ckeditor = CKEditor.component
const editor = ClassicEditor
const editorData = ref('')
const editorConfig = {}

const title = ref('')

onMounted(() => {
  // console.log( document.querySelector("#inputFileUploadInsert") )
  //initUI()
})



const initUI = () => {
  title.value = ''
  editorData.value = ''
}

const emit = defineEmits(['call-parent-fdhinsert'])
const closeModal = () => emit('call-parent-fdhinsert')

const fdhInsert = async () => {
  console.log("제목 ", title.value)
  console.log("내용 ",editorData.value)
  let formData = new FormData()
  formData.append('name', title.value)
  formData.append('content', editorData.value)

  let options = {
    headers: { 'Content-Type': 'multipart/form-data' }
  }

  try {
    let { data } = await http.post('/chat/createRoom', formData, options)

    console.log(data)
    // if (data.result == 'login') {
    //   doLogout()
    // } else {
    // this.$alertify.success('글이 등록되었습니다.');
    closeModal()
    //}
  } catch (error) {
    console.log('InsertModalVue: error ')
    console.log(error)
  }
}

</script>
<style scoped>
.modal:deep(.ck-editor__editable) {
  min-height: 300px !important;
}

.modal:deep(.thumbnail-wrapper) {
  margin-top: 5px;
}

.modal:deep(.thumbnail-wrapper img) {
  width: 100px !important;
  margin-right: 5px;
  max-width: 100%;
}
</style>