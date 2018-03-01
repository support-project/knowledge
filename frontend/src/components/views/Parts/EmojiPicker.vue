<template>
  <span>
    <div class="modal fade" id="modal-emoji" style="display: none;">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">×</span></button>
            <h4 class="modal-title">{{$t('EmojiPicker.Emoji')}}</h4>
          </div>
          <div class="modal-body text-center">
            <picker title="Pick your emoji…" emoji="point_up" :perLine="perLine" @click="emojiSelect"></picker>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">{{$t('Label.Close')}}</button>
          </div>
        </div>
      </div>
    </div>
    <button type="button" class="btn btn-link" data-toggle="modal" data-target="#modal-emoji">
      &#x1F600;{{$t('EmojiPicker.Emoji')}}
    </button>
  </span>
</template>

<script>
/* global $ */
import { Picker } from 'emoji-mart-vue'
import logger from 'logger'
const LABEL = 'EmojiPicker.vue'

export default {
  name: 'EmojiPicker',
  props: ['contents'],
  components: { Picker },
  data () {
    return {
      perLine: 15
    }
  },
  methods: {
    emojiSelect: function (emoji) {
      logger.debug(LABEL, JSON.stringify(emoji))
      this.$emit('emoji-select', emoji.native)
      $('#modal-emoji').modal('hide')
    },
    setWindowWidth: function () {
      setTimeout(() => {
        if (window.innerWidth < 768) {
          this.perLine = 8
        } else {
          this.perLine = 15
        }
      }, 300)
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.setWindowWidth()
    })
  },
  created: function () {
    // インスタンスを作成した後、イベントリスナに登録
    window.addEventListener('resize', this.setWindowWidth, false)
  },
  beforeDestroy: function () {
    // インスタンスを破棄する前に、イベントリスナから削除
    window.removeEventListener('resize', this.setWindowWidth, false)
  }
}
</script>

<style>

</style>
