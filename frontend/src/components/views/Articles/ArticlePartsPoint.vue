<template>

  <span class="article-points">
    <span class="">
      <i class="fa fa-heart-o" aria-hidden="true"></i>&nbsp;CP × {{article.point}}
    </span>

    <span class="article-point-value" v-if="article.pointOnTerm"> <!-- only popular list -->
      <i class="fa fa-line-chart" aria-hidden="true"></i>&nbsp;× {{article.pointOnTerm}} &nbsp;
    </span>

    <span class="article-point-value">
      <a href="open.knowledge/likes/" class="text-primary btn-link">
        <i class="fa fa-thumbs-o-up"></i>&nbsp;Like × <span id="like_count">{{article.likeCount}}</span>
      </a>
    </span>

    <span class="article-point-value">
      <a href="#comments" id="commentsLink" class="text-primary btn-link inner-page-link">
        <i class="fa fa-comments-o"></i>&nbsp;Comments × {{article.commentCount}}
      </a>
    </span>

    <span class="article-point-value">
      <a href="#attachFilesPanel" id="attachFilesLink" class="text-primary btn-link inner-page-link"
        v-if="article.attachments">
        <i class="fa fa-download"></i>&nbsp;Attach × {{article.attachments ? article.attachments.length : 0 }}
      </a>
    </span>

    <span class="article-point-value">
      <a id="eventInfoLink" class="text-primary btn-link" v-if="article.typeId === -101">
        <i class="fa fa-users"></i>&nbsp;participants × {{article.commentCount}}
      </a>
    </span>

  </span>

</template>

<script>
/* global $ */
export default {
  name: 'ArticlePartsPoint',
  props: ['article'],
  mounted () {
    this.$nextTick(() => {
      $('.inner-page-link').click(function () {
        var speed = 500
        var href = $(this).attr('href')
        var target = $(href)
        if (target && target.offset && target.offset()) {
          var position = target.offset().top
          position = position - 100
          $('html, body').animate({scrollTop: position}, speed, 'swing')
        }
        return false
      })
    })
  }
}
</script>

<style>
.article-points .article-point-value {
  margin-left: 20px;
}
</style>
