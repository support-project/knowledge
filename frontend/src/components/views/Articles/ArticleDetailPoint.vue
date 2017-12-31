<template>

  <div class="row article-point">
    <div class="col-xs-6 col-sm-2">
        <i class="fa fa-heart-o" aria-hidden="true"></i>&nbsp;CP × {{article.point}}
    </div>
    <div class="col-xs-6 col-sm-2">
      <a href="open.knowledge/likes/" class="text-primary btn-link">
          <i class="fa fa-thumbs-o-up"></i>&nbsp;Like × <span id="like_count">{{article.likeCount}}</span>
      </a>
    </div>
    <div class="col-xs-6 col-sm-2">
        <a href="#comments" id="commentsLink" class="text-primary btn-link inner-page-link">
            <i class="fa fa-comments-o"></i>&nbsp;Comments × {{article.commentCount}}
        </a>
    </div>
    <div class="col-xs-6 col-sm-2">
        <a href="#attachFilesPanel" id="attachFilesLink" class="text-primary btn-link inner-page-link">
            <i class="fa fa-download"></i>&nbsp;Attach × {{article.attachments ? article.attachments.length : 0 }}
        </a>
    </div>
    <!-- イベントのときの参加者 -->
    <div class="col-xs-6 col-sm-2" v-if="article.typeId === -101">
        <a id="eventInfoLink" class="text-primary btn-link">
            <i class="fa fa-users"></i>&nbsp;participants × {{article.commentCount}}
        </a>
    </div>

  </div>

</template>

<script>
/* global $ */
export default {
  name: 'ArticleDetailPoint',
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
.article-point {
  margin-bottom: 20px;
}

</style>

