<template>
  <!-- contents -->
  <div class="form-group">
    <ul class="nav nav-tabs">
      <li class="active"><a href="#contentsTab" data-toggle="tab">
        Contents
      </a></li>
      <li><a href="#previewTab" data-toggle="tab" v-on:click="preview()">
        Preview
      </a></li>
    </ul>
    <div class="tab-content">
      <div class="tab-pane contents active" id="contentsTab">
        <textarea class="form-control" name="content" rows="15" placeholder="Markdown" id="content"
        v-model="article.content"></textarea>

        <article-parts-emoji @emoji-select="selectEmojiToCotents" />

        <span class="helpMarkdownLabel pull-right">
            <a data-toggle="modal" data-target="#helpMarkdownModal"><i class="fa fa-info-circle" aria-hidden="true"></i>Markdown information</a>
        </span>
        
      </div>

      <div class="tab-pane contents" id="previewTab">
        <div class="markdown-body">
          <span id="presentationArea" class="slideshow"></span>
        </div>
        <div class="markdown-body">
          <span v-html="article.displaySafeHtml"></span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/* global $ */
import ArticlePartsEmoji from './ArticlePartsEmoji'
import processFootnotesPotision from '../../../lib/displayParts/processFootnotesPotision'

export default {
  name: 'ArticleEditContents',
  props: ['article'],
  components: { ArticlePartsEmoji },
  methods: {
    selectEmojiToCotents: function (emoji) {
      var obj = $('#content')
      obj.focus()
      var s = obj.val()
      var p = obj.get(0).selectionStart
      var np = p + emoji.length
      var text = s.substr(0, p) + emoji + s.substr(p)
      this.article.content = text
      setTimeout(() => {
        obj.get(0).setSelectionRange(np, np)
        obj.focus()
      }, 500)
    },
    preview: function () {
      this.$store.dispatch('previewArticle', this.article)
      .then(() => {
        setTimeout(() => {
          processFootnotesPotision($('.markdown-body'))
        }, 500)
      })
    }
  }
}
</script>

<style>
.tab-pane.contents.active {
  background-color: white;
  border-left: 1px solid #ddd;
  border-right: 1px solid #ddd;
  border-bottom: 1px solid #ddd;
  padding: 20px;
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
  border-bottom-left-radius: 10px;
  -webkit-border-top-right-radius: 10px;  
  -webkit-border-bottom-right-radius: 10px;  
  -webkit-border-bottom-left-radius: 10px;  
  -moz-border-radius-topright: 10px;  
  -moz-border-radius-bottomright: 10px;  
  -moz-border-radius-bottomleft: 10px;  
}
</style>
