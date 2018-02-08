<template>
  <span>
    <a class="text-primary btn-link">
        <i class="fa fa-thumbs-o-up"></i>
        &nbsp;Like × {{comment.likeCount}}
    </a>
    &nbsp;
    <button class="btn btn-info btn-circle btn-xs" v-on:click="likeComment(comment.knowledgeId, comment.commentNo)">
        <i class="fa fa-thumbs-o-up"></i>&nbsp;
    </button>
    &nbsp;
    &nbsp;

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
import logger from 'logger'
const LABEL = 'ArticleDetailCommentsButton.vue'

export default {
  name: 'ArticleDetailCommentsButton',
  props: ['comment', 'article'],
  methods: {
    collapse: function (comment, flag) {
      comment.commentStatus = flag
      this.$store.dispatch('comments/toggleDisplayComment', {
        id: this.comment.knowledgeId,
        comment: this.comment
      })
    },
    likeComment (id, commentNo) {
      this.$store.dispatch('comments/likeComment', { id, commentNo }).then((cnt) => {
        logger.debug(LABEL, JSON.stringify(cnt))
      })
    }
  }
}
</script>

<style>

</style>
