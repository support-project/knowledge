<template>
  <div class="modal fade" id="_modalDialg">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span></button>
          <h4 class="modal-title">
            {{$t('_ModalDialog.Title')}}
          </h4>
        </div>

        <div class="modal-body">
          
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">{{$t('Label.Close')}}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: '_PartsTemplate',
  data () {
    return {
    }
  },
  computed: {
    ...mapState({
      pagestate: state => state.pagestate
    })
  },
  methods: {
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

<style>

</style>
