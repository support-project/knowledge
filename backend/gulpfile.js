var gulp = require('gulp');
var usemin = require('gulp-usemin');
var uglify = require('gulp-uglify');
var minifyHtml = require('gulp-minify-html');
var minifyCss = require('gulp-minify-css');
var rev = require('gulp-rev');
var replace = require('gulp-replace');
var eslint = require('gulp-eslint');
var rename = require("gulp-rename");  
var rimraf = require('rimraf');
var runSequence = require('run-sequence');

gulp.task('min', function() {
    return gulp.src([
        'src/main/webapp/WEB-INF/views/**/*.jsp'
    ])
    .pipe(replace('href="<%= request.getContextPath() %>/bower', 'href="/bower'))
    .pipe(replace('href="<%= request.getContextPath() %>/css', 'href="/css'))
    .pipe(replace('src="<%= request.getContextPath() %>/bower', 'src="/bower'))
    .pipe(replace('src="<%= request.getContextPath() %>/js', 'src="/js'))
    .pipe(replace('<%= request.getContextPath() %>/EasyWizard', '/EasyWizard'))
    .pipe(usemin({
        css: [rev],
        htmlmin: [ function () {return minifyHtml({ empty: true });} ],
        js: [uglify, rev],
        inlinejs: [ uglify ],
        inlinecss: [ minifyCss, 'concat' ],
        outputRelativePath: '../../'
    }))
    .pipe(replace('var _LOGGING_NOTIFY_DESKTOP = true;', 'var _LOGGING_NOTIFY_DESKTOP = false;'))
    .pipe(replace('href="/bower', 'href="<%= request.getContextPath() %>/bower'))
    .pipe(replace('href="/css', 'href="<%= request.getContextPath() %>/css'))
    .pipe(replace('src="/bower', 'src="<%= request.getContextPath() %>/bower'))
    .pipe(replace('src="/js', 'src="<%= request.getContextPath() %>/js'))
    .pipe(replace('href="bower', 'href="<%= request.getContextPath() %>/bower'))
    .pipe(replace('href="css', 'href="<%= request.getContextPath() %>/css'))
    .pipe(replace('src="bower', 'src="<%= request.getContextPath() %>/bower'))
    .pipe(replace('src="js', 'src="<%= request.getContextPath() %>/js'))
    .pipe(gulp.dest('target/knowledge/WEB-INF/views/'));
});

gulp.task('copy', ['copy:bootswatch', 'copy:bootswatch2', 'copy:highlightjs', 'copy:font-awesome', 'copy:flag-icon-css', 
    'copy:html5shiv', 'copy:respond', 'copy:MathJax', 'copy:emoji-parser', 'copy:free-file-icons',
    'copy:diff2html', 'copy:jsdiff', 'copy:jspdf', 'copy:pdfthema']);

gulp.task('copy:bootswatch', function() {
    return gulp.src([
        'src/main/webapp/bower/bootswatch/**/*'
    ])
    .pipe(gulp.dest('target/knowledge/bower/bootswatch'));
});
gulp.task('copy:bootswatch2', function() {
    return gulp.src([
        'src/main/webapp/bower/bootswatch/**/*.css'
    ])
    .pipe(replace(/^@import url\("https:\/\/fonts.googleapis.com\/css.*\)\;/, ''))
    .pipe(gulp.dest('target/knowledge/bower/bootswatch'));
});
gulp.task('copy:highlightjs', function() {
    return gulp.src([
        'src/main/webapp/bower/highlightjs/**/*'
    ])
    .pipe(gulp.dest('target/knowledge/bower/highlightjs'));
});
gulp.task('copy:font-awesome', function() {
    return gulp.src([
        'src/main/webapp/bower/font-awesome/**/*'
    ])
    .pipe(gulp.dest('target/knowledge/bower/font-awesome'));
});
gulp.task('copy:flag-icon-css', function() {
    return gulp.src([
        'src/main/webapp/bower/flag-icon-css/**/*'
    ])
    .pipe(gulp.dest('target/knowledge/bower/flag-icon-css'));
});
gulp.task('copy:html5shiv', function() {
    return gulp.src([
        'src/main/webapp/bower/html5shiv/**/*'
    ])
    .pipe(gulp.dest('target/knowledge/bower/html5shiv'));
});
gulp.task('copy:respond', function() {
    return gulp.src([
        'src/main/webapp/bower/respond/**/*'
    ])
    .pipe(gulp.dest('target/knowledge/bower/respond'));
});
gulp.task('copy:MathJax', function() {
    return gulp.src([
        'src/main/webapp/bower/MathJax/**/*'
    ])
    .pipe(gulp.dest('target/knowledge/bower/MathJax'));
});
gulp.task('copy:emoji-parser', function() {
    return gulp.src([
        'src/main/webapp/bower/emoji-parser/**/*'
    ])
    .pipe(gulp.dest('target/knowledge/bower/emoji-parser'));
});
gulp.task('copy:free-file-icons', function() {
    return gulp.src([
        'src/main/webapp/bower/teambox.free-file-icons/**/*'
    ])
    .pipe(gulp.dest('target/knowledge/bower/teambox.free-file-icons'));
});
gulp.task('copy:diff2html', function() {
    return gulp.src([
        'src/main/webapp/bower/diff2html/**/*'
    ])
    .pipe(gulp.dest('target/knowledge/bower/diff2html'));
});
gulp.task('copy:jsdiff', function() {
    return gulp.src([
        'src/main/webapp/bower/jsdiff/**/*'
    ])
    .pipe(gulp.dest('target/knowledge/bower/jsdiff'));
});
gulp.task('copy:jspdf', function() {
    return gulp.src([
        'src/main/webapp/bower/jspdf/**/*'
    ])
    .pipe(gulp.dest('target/knowledge/bower/jspdf'));
});
gulp.task('copy:pdfthema', function() {
    return gulp.src([
        'src/main/webapp/css/presentation-thema/**/*'
    ])
    .pipe(gulp.dest('target/knowledge/css/presentation-thema'));
});

gulp.task('gen', function(callback) {
    runSequence('gen:rmstart', 'gen:index', 'gen:rm', callback);
});
gulp.task('gen:rmstart', function(callback) {
    rimraf('./src/main/webapp/index.jsp', callback);
})
gulp.task('gen:index', function() {
    return gulp.src([
        'src/main/webapp/index.html'
    ])
    .pipe(replace('/static', 'static'))
    .pipe(replace('<base href=/ >', '<base href="<%= request.getContextPath() %>/" >'))
    .pipe(replace('/knowledgestatic/', 'static/'))
    .pipe(replace('<!DOCTYPE html>', '<%@page pageEncoding="UTF-8"%><!DOCTYPE html>'))
    .pipe(rename('index.jsp'))
    .pipe(gulp.dest('target/knowledge/'));
});
gulp.task('gen:rm', function(callback) {
    rimraf('./src/main/webapp/index.html', callback);
})
    
gulp.task('check', function () {
    return gulp.src(['src/main/webapp/js/slide.js'])
    .pipe(eslint())
    .pipe(eslint.format())
    .pipe(eslint.failAfterError());
});

gulp.task('default', ['min', 'copy', 'gen']);
