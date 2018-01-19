<template>
  <span>
    <span v-html="comment.displaySafeHtml"></span>

    <hr class="hrstyle01"/>

    <!-- コメントに付けた添付ファイルの表示 
    <c:forEach var="file" items="${files}">
        <c:if test="${file.commentNo == comment.commentNo}">
            <div class="downloadfile">
                <img src="<%=jspUtil.out("file.thumbnailUrl")%>" /> <a href="<%=jspUtil.out("file.url")%>&amp;attachment=true"> <%=jspUtil.out("file.name")%>
                </a>
            </div>
        </c:if>
    </c:forEach>
    -->
    
    <a class="text-primary btn-link">
        <i class="fa fa-thumbs-o-up"></i>
        &nbsp;Like × {{comment.likeCount}}
    </a>
    &nbsp;
    <button class="btn btn-info btn-circle" v-on:click="likeComment(comment.knowledgeId, comment.commentNo)">
        <i class="fa fa-thumbs-o-up"></i>&nbsp;
    </button>
  </span>
</template>

<script>
import { Notification } from 'uiv'
import logger from 'logger'
const LABEL = 'ArticleDetailCommentsContents.vue'

export default {
  name: 'ArticleDetailCommentsContents',
  props: ['comment', 'article'],
  methods: {
    likeComment (id, commentNo) {
      this.$store.dispatch('likeComment', { id, commentNo }).then((cnt) => {
        logger.debug(LABEL, JSON.stringify(cnt))
        Notification.notify({
          type: 'success',
          title: 'Well done!',
          content: 'You successfully added Like.'
        })
      })
    }
  }
}
</script>

<style>
/* circle button style */
.btn-circle.btn-xs{width:22px;height:22px;font-size:10px;border-radius:11px;line-height:1.6;padding:3px 0;}
.btn-circle.btn-sm{width:30px;height:30px;font-size:12px;border-radius:15px;line-height:1.6;padding:6px 0;}
.btn-circle       {width:34px;height:34px;font-size:16px;border-radius:17px;line-height:1.33;padding:6px 4px;text-align:center;}
.btn-circle.btn-lg{width:46px;height:46px;font-size:22px;border-radius:23px;line-height:1.2;}

/* round button style */
.btn-round.btn-xs{border-radius:11px;}
.btn-round.btn-sm{border-radius:22.5px;}
.btn-round       {border-radius:17px;}
.btn-round.btn-lg{border-radius:23px;}
</style>
