import getArticle from './actions/getArticle'
import getArticleForEdit from './actions/getArticleForEdit'
import getDraftForEdit from './actions/getDraftForEdit'
import previewArticle from './actions/previewArticle'
import saveArticle from './actions/saveArticle'
import saveDraft from './actions/saveDraft'
import deleteDraft from './actions/deleteDraft'
import likeArticle from './actions/likeArticle'
import removeViewer from './actions/removeViewer'
import removeEditor from './actions/removeEditor'
import addTag from './actions/addTag'
import removeTag from './actions/removeTag'

import initArticle from './mutations/initArticle'
import setArticle from './mutations/setArticle'
import changeArticleType from './mutations/changeArticleType'
import setToc from './mutations/setToc'

export default {
  namespaced: true,
  state: {
    article: {
      knowledgeId: '',
      title: '',
      content: '',
      draftId: '',
      publicFlag: 1,
      type: {
        id: -100,
        items: []
      },
      viewers: {
        groups: [],
        users: []
      },
      editors: {
        groups: [],
        users: []
      },
      tags: []
    },
    toc: ''
  },
  getters: {
  },
  actions: {
    getArticle: getArticle,
    getArticleForEdit: getArticleForEdit,
    getDraftForEdit: getDraftForEdit,
    saveArticle: saveArticle,
    saveDraft: saveDraft,
    deleteDraft: deleteDraft,
    previewArticle: previewArticle,
    likeArticle: likeArticle,
    removeViewer: removeViewer,
    removeEditor: removeEditor,
    addTag: addTag,
    removeTag: removeTag
  },
  mutations: {
    initArticle: initArticle,
    setArticle: setArticle,
    changeArticleType: changeArticleType,
    setToc: setToc
  }
}
