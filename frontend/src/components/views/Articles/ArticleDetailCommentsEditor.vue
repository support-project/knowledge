<template>
  <span>
    [{{$t('Label.Create')}}]
    {{comment.insertDatetime | moment}}
    [{{comment.insertUserName}}]
    <span v-if="comment.insertDatetime !== comment.updateDatetime">
    <br />
    [{{$t('Label.Update')}}]
    {{comment.updateDatetime | moment}}
    [{{comment.updateUserName}}]
    </span>
  </span>
</template>

<script>
import moment from 'moment'
export default {
  name: 'ArticleDetailCommentsEditor',
  props: ['comment', 'article'],
  filters: {
    moment: function (date) {
      return moment(date).format('YYYY/MM/DD HH:mm')
    }
  }
}
</script>

<style>

</style>
