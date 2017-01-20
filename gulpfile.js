var gulp = require('gulp');
var usemin = require('gulp-usemin');
var uglify = require('gulp-uglify');
var minifyHtml = require('gulp-minify-html');
var minifyCss = require('gulp-minify-css');
var rev = require('gulp-rev');
var replace = require('gulp-replace');

gulp.task('min', function() {
    return gulp.src([
        'src/main/webapp/WEB-INF/views/**/*.jsp'
    ])
    .pipe(usemin({
        css: [rev],
        htmlmin: [ function () {return minifyHtml({ empty: true });} ],
        js: [uglify, rev],
        inlinejs: [ uglify ],
        inlinecss: [ minifyCss, 'concat' ],
        outputRelativePath: '../../'
    }))
    .pipe(gulp.dest('target/knowledge/WEB-INF/views/'));
});

gulp.task('copy', ['copy:bootswatch', 'copy:highlightjs', 'copy:font-awesome', 'copy:flag-icon-css', 
    'copy:html5shiv', 'copy:respond', 'copy:MathJax', 'copy:emoji-parser', 'copy:free-file-icons']);
gulp.task('copy:bootswatch', function() {
    return gulp.src([
        'src/main/webapp/bower/bootswatch/**/*'
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


gulp.task('default', ['min', 'copy']);
