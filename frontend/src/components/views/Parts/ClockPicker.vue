<template>
  <span>
    <input type="text" class="form-control" :value="value" @input="updateDate()"
      ref="clockPicker" :id="id" readonly v-on:keyup.8="clearDate"/>
  </span>
</template>

<script>
/* global $ */
import 'clockpicker/dist/bootstrap-clockpicker'
import 'clockpicker/dist/bootstrap-clockpicker.min.css'

export default {
  props: ['value', 'id'],
  methods: {
    updateDate () {
      this.$emit('input', +this.$refs.clockPicker.value)
    },
    clearDate () {
      this.value = ''
    }
  },
  mounted () {
    this.$nextTick(() => {
      const pageContext = this
      const id = this.id
      $('#' + id).clockpicker({
        autoclose: true,
        default: this.value,
        afterHide: function () {
          pageContext.$emit('input', $('#' + id).val())
        },
        afterDone: function () {
          pageContext.$emit('input', $('#' + id).val())
        }
      })
    })
  }
}
</script>
