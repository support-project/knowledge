export default (state, resources) => {
  if ('article' in resources) {
    throw new Error('deplicated article')
  }
  if ('articles' in resources) {
    throw new Error('deplicated articles')
  }
  if ('drafts' in resources) {
    state.resources.drafts = resources.drafts
  }
  if ('comments' in resources) {
    state.resources.comments = resources.comments
  }
  if ('groups' in resources) {
    state.resources.groups = resources.groups
  }
  if ('types' in resources) {
    state.resources.types = resources.types
  }
  if ('tags' in resources) {
    state.resources.tags = resources.tags
  }
  if ('toc' in resources) {
    state.resources.toc = resources.toc
  }
}
