<template>
  <span>
    <a class="btn btn-primary btn-xs"
        href="<%=request.getContextPath()%>/protect.knowledge/edit_comment/<%=comment.getCommentNo()%>"> <i class="fa fa-edit"></i>
        {{$t('Label.Edit')}}
    </a>
    &nbsp;
    <button class="btn btn-info btn-xs"
        v-on:click="collapse(comment, 1)"
        v-if="comment.commentStatus !== 1">
        <i class="fa fa-minus-square-o"></i>
        {{$t('Label.Hide')}}
    </button>
    &nbsp;
    <button class="btn btn-warning btn-xs"
        v-on:click="collapse(comment, 0)"
        v-if="comment.commentStatus === 1">
        <i class="fa fa-plus-square-o"></i>
        {{$t('Label.Show')}}
    </button>
  </span>
</template>

<script>
export default {
  name: 'ArticleDetailCommentsButton',
  props: ['comment', 'article'],
  methods: {
    collapse: function (comment, flag) {
      comment.commentStatus = flag
      // TODO 保存
    }
  }
}
</script>

<style>

</style>
