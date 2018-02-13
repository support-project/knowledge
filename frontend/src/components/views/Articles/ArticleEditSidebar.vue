<template>
  <div>
    <aside class="control-sidebar control-sidebar-dark"
      v-bind:class="{'control-sidebar-open': pagestate.showRightSideBar}">
      <div class="tab-content" id="tab-content">
        <!-- Home tab content -->
        <div class="tab-pane active" id="control-sidebar-theme-demo-options-tab">
          <div class="close-btn-on-sidebar">
            <a class="btn btn-link" v-on:click="toggleRightSideBar()">
              <i class="fa fa-angle-double-right fa-2x" aria-hidden="true"></i>
            </a>
          </div>

          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Types</h3>
            </div>
            <div class="box-body text-black" v-for="t in types" :key="t.id">
              <label v-if="article.type.id" >
                <input type="radio" name="type" :value="t.id"
                  v-model="article.type.id" v-on:change="changeType(t.id)">
                <i :class="'fa ' + t.icon" aria-hidden="true"></i>
                {{ t.name | abbreviate(20)}}
              </label>
            </div>
          </div>

          <article-parts-viewers-select />

          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Tags</h3>
              <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool">
                  <i class="fa fa-plus-circle"></i>
                </button>
              </div>
            </div>
            <div class="box-body text-black">
              <div class="box-tools">
                <tooltip text="already exist" trigger="manual" v-model="tagInputTooltip">
                  <div class="input-group input-group-sm" style="width: 200px;">
                    <input type="text" name="inputTag" id="inputTag" class="form-control pull-right"
                      placeholder="Tag" autocomplete="off">
                    <div class="input-group-btn">
                      <button type="submit" class="btn btn-default" v-on:click="addTag">
                        <i class="fa fa-plus-circle"></i>
                      </button>
                    </div>
                  </div>
                </tooltip>
              </div>
              <typeahead v-model="tag" target="#inputTag" :async-function="queryFunction" item-key="tagName" />
            </div>
            <div class="box-body text-black" v-for="tag in article.tags" :key="tag">
                <i class="fa fa-tag" aria-hidden="true"></i>
                {{ tag }}
                <div class="box-tools pull-right">
                  <button type="button" class="btn btn-box-tool" v-on:click="removeTag(tag)">
                    <i class="fa fa-minus-circle"></i>
                  </button>
                </div>
            </div>
          </div>

          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Editor</h3>
              <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" v-on:click="selectEditors()">
                  <i class="fa fa-plus-circle"></i>
                </button>
              </div>
            </div>

            <div if="article.viewers">
              <div v-for="vgroup in article.editors.groups" :key="vgroup.id" class="box-body text-black">
                <i class="fa fa-users text-light-blue" aria-hidden="true"></i>&nbsp;{{vgroup.name | abbreviate(20)}}
                <div class="box-tools pull-right">
                  <button type="button" class="btn btn-box-tool" v-on:click="removeEditor(vgroup)"><i class="fa fa-minus-circle"></i></button>
                </div>
              </div>
            </div>

            <div if="article.viewers">
              <div v-for="vuser in article.editors.users" :key="vuser.id" class="box-body text-black">
                <i class="fa fa-user text-olive" aria-hidden="true"></i>&nbsp;{{vuser.name | abbreviate(20)}}
                <div class="box-tools pull-right">
                  <button type="button" class="btn btn-box-tool" v-on:click="removeEditor(vuser)"><i class="fa fa-minus-circle"></i></button>
                </div>
              </div>
            </div>
          </div>


        </div>
      </div>
    </aside>

    <div class="control-sidebar-bg"></div>

  </div>
</template>

<script>
import { Tooltip, Typeahead } from 'uiv'

import { mapState } from 'vuex'
import logger from 'logger'

import api from '../../../api'

import ArticlePartsViewersSelect from './ArticlePartsViewersSelect'

const LABEL = 'ArticleEditSidebar.vue'

export default {
  name: 'ArticleEditSidebar',
  data () {
    return {
      tag: '',
      tagInputTooltip: false,
      suggestTags: ['aaa', 'bbb']
    }
  },
  watch: {
    'tag': 'clearTagInputTooltip'
  },
  computed: {
    ...mapState({
      pagestate: state => state.pagestate,
      article: state => state.article.article,
      types: state => state.types.types
    })
  },
  components: {ArticlePartsViewersSelect, Tooltip, Typeahead},
  methods: {
    toggleRightSideBar () {
      this.$store.dispatch('pagestate/toggleRightSideBar')
    },
    changeType (type) {
      logger.debug(LABEL, type)
      this.$store.commit('article/changeArticleType', {
        type: type,
        types: this.types
      })
    },
    clearTagInputTooltip () {
      this.tagInputTooltip = false
    },
    addTag () {
      logger.info(LABEL, 'addTag: ' + this.tag)
      this.tagInputTooltip = false
      this.$store.dispatch('article/addTag', this.tag).then(result => {
        if (result) {
          this.tag = ''
        } else {
          this.tagInputTooltip = true
        }
      })
    },
    removeTag (tag) {
      this.$store.dispatch('article/removeTag', tag)
    },
    queryFunction (query, done) {
      api.request('get', '/_api/tags?keyword=' + this.tag, null).then(res => {
        return done(res.data)
      })
    },
    selectEditors: function () {
      this.$store.dispatch('targets/showDialog', {
        selected: this.article.editors
      })
    },
    removeEditor: function (target) {
      this.$store.dispatch('article/removeEditor', target)
    }
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

<style>
.sidebar-group {
  margin-bottom: 10px;
}
.sidebar-group .sidebar-title {
  margin-bottom: 10px;
}
</style>

<style src="../../css/toc.css" />
<style src="../../css/control-sidebar.css" />
