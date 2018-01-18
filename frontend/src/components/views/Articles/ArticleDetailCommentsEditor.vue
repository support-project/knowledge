<template>
  <span>
    [{{$t('Label.Create')}}]
    {{comment.insertDatetime | dispDate}}
    [{{comment.insertUserName}}]
    <span v-if="comment.insertDatetime !== comment.updateDatetime">
    <br />
    [{{$t('Label.Update')}}]
    {{comment.updateDatetime | dispDate}}
    [{{comment.updateUserName}}]
    </span>
  </span>
</template>

<script>
export default {
  name: 'ArticleDetailCommentsEditor',
  props: ['comment', 'article']
}
</script>

<style>

</style>
