import DashView from './components/Dash.vue'
import LoginView from './components/Login.vue'
import NotFoundView from './components/404.vue'

// Import Views - Dash
import ArticleList from './components/views/Articles/ArticleList.vue'
import ArticleDetail from './components/views/Articles/ArticleDetail.vue'
import ArticleSearch from './components/views/Articles/ArticleSearch.vue'
import ArticleEdit from './components/views/Articles/ArticleEdit.vue'
import DraftList from './components/views/Articles/DraftList.vue'
import StockList from './components/views/Stocks/StockList.vue'
import StockArticles from './components/views/Stocks/StockArticles.vue'
import StockAdd from './components/views/Stocks/StockAdd.vue'
import Config from './components/views/Configs/Config.vue'
import ConfigLocalize from './components/views/Configs/ConfigLocalize.vue'

/*
import TestView from './components/views/Test.vue'
import DashboardView from './components/views/Dashboard.vue'
import TablesView from './components/views/Tables.vue'
import TasksView from './components/views/Tasks.vue'
import SettingView from './components/views/Setting.vue'
import AccessView from './components/views/Access.vue'
import ServerView from './components/views/Server.vue'
import ReposView from './components/views/Repos.vue'
*/

// Routes
const routes = [
  {
    path: '/login',
    component: LoginView
  },
  {
    path: '/',
    component: DashView,
    children: [
      {
        path: 'articles',
        alias: '',
        component: ArticleList,
        name: 'ArticleList'
      }, {
        path: 'articles/search',
        component: ArticleSearch,
        name: 'ArticleSearch',
        meta: {requiresAuth: false}
      }, {
        path: 'articles/new',
        component: ArticleEdit,
        name: 'ArticleCreate',
        meta: {requiresAuth: true}
      }, {
        path: 'articles/:id',
        component: ArticleDetail,
        name: 'ArticleDetail'
      }, {
        path: 'articles/:id/edit',
        component: ArticleEdit,
        name: 'ArticleEdit',
        meta: {requiresAuth: true}
      }, {
        path: 'drafts',
        component: DraftList,
        name: 'DraftList',
        meta: {requiresAuth: true}
      }, {
        path: 'drafts/:draftId/edit',
        component: ArticleEdit,
        name: 'DraftEdit',
        meta: {requiresAuth: true}
      }, {
        path: 'myarticles',
        component: ArticleList,
        name: 'MyArticleList',
        meta: {requiresAuth: true}
      }, {
        path: 'stocks',
        component: StockList,
        name: 'StockList',
        meta: {requiresAuth: true}
      }, {
        path: 'stocks/add',
        component: StockAdd,
        name: 'StockAdd',
        meta: {requiresAuth: true}
      }, {
        path: 'stocks/:id/articles',
        component: StockArticles,
        name: 'StockArticles',
        meta: {requiresAuth: true}
      }, {
        path: 'config',
        component: Config,
        name: 'Config',
        meta: {requiresAuth: true}
      }, {
        path: 'localize',
        component: ConfigLocalize,
        name: 'ConfigLocalize',
        meta: {requiresAuth: false}
/*
      }, {
        path: 'test',
        component: TestView,
        name: 'TestViewTitle',
        meta: {description: 'TestViewDescription'}
      }, {
        path: 'dashboard',
        component: DashboardView,
        name: 'Dashboard',
        meta: {description: 'Overview of environment'}
      }, {
        path: 'tables',
        component: TablesView,
        name: 'Tables',
        meta: {description: 'Simple and advance table in CoPilot'}
      }, {
        path: 'tasks',
        component: TasksView,
        name: 'Tasks',
        meta: {description: 'Tasks page in the form of a timeline'}
      }, {
        path: 'setting',
        component: SettingView,
        name: 'Settings',
        meta: {description: 'User settings page'}
      }, {
        path: 'access',
        component: AccessView,
        name: 'Access',
        meta: {description: 'Example of using maps'}
      }, {
        path: 'server',
        component: ServerView,
        name: 'Servers',
        meta: {description: 'List of our servers', requiresAuth: true}
      }, {
        path: 'repos',
        component: ReposView,
        name: 'Repository',
        meta: {description: 'List of popular javascript repos'}
*/
      }
    ]
  }, {
    // not found handler
    path: '*',
    component: NotFoundView
  }
]

export default routes
