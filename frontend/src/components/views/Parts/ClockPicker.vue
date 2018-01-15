<template>
  <span>
    <input type="text" class="form-control" :value="value" @input="updateDate()"
      ref="clockPicker" :id="id"/>
  </span>
</template>

<script>
/* global $ */
import '../../../../node_modules/clockpicker/dist/bootstrap-clockpicker'
import '../../../../node_modules/clockpicker/dist/bootstrap-clockpicker.min.css'

export default {
  props: ['value', 'id'],
  methods: {
    updateDate () {
      this.$emit('input', +this.$refs.clockPicker.value)
    }
  },
  mounted () {
    this.$nextTick(() => {
      console.log(this.value)
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
