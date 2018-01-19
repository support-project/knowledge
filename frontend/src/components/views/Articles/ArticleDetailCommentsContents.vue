<template>
  <span>
    <div class="markdown-body" v-if="comment.commentStatus != 1">
      <span v-html="comment.displaySafeHtml"></span>
    </div>
    <div class="markdown-body text-gray" v-if="comment.commentStatus == 1">
      {{$t('ArticleDetailComments.DisplayHidden')}}
    </div>

    <!--
    <hr class="hrstyle01"/>
    -->

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
  </span>
</template>

<script>
export default {
  name: 'ArticleDetailCommentsContents',
  props: ['comment', 'article']
}
</script>
