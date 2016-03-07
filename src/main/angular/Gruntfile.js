module.exports = function (grunt) {
    // Project configuration.
    grunt.initConfig({
        connect: {
            server: {
                options: {
                    port: 3000,
                    base: 'app',
                    hostname: 'localhost',
                    livereload: true,
                    middleware: function (connect, options, middlewares) {
                        middlewares.unshift(require('grunt-connect-proxy/lib/utils').proxyRequest);
                        return middlewares;
                    }
                },
                proxies: [
                    {
                        context: ['/webresources', '/bvrules'],
                        rewrite: {
                            '^/bvrules': '/gedoplan-webclients/bvrules',
                            '^/webresources': '/gedoplan-webclients/webresources'
                        },
                        host: 'localhost',
                        port: 8080,
                        https: false,
                        xforward: true
                    }
                ]
            }
        },
        ngAnnotate: {
            build: {
                files: [{
                        expand: true,
                        src: ['app/{services,components}/**/*.js','app/*.js'],
                        dest: '.tmp'
                    }]
            }
        },
        useminPrepare: {
            html: 'app/index.html',
            options: {
                dest: 'dist'
            }
        },
        usemin: {
            html: ['dist/{,*/}*.html'],
            css: ['dist/styles/{,*/}*.css'],
            js: ['dist/scripts/{,*/}*.js']
        },
        clean: {
            dist: ['.tmp', 'dist', '*.war']
        },
        copy: {
            html: {
                src: '{,**/}*.html',
                expand: true,
                cwd: 'app',
                dest: 'dist/'
            },
            index: {
                src: 'index.html',
                expand: true,
                cwd: 'app',
                dest: 'dist/'
            },
            assets: {
                src: '{fonts,i18n,img}/**',
                expand: true,
                cwd: 'app/assets',
                dest: 'dist/assets'
            },
            final: {
                src: '**',
                expand: true,
                cwd: 'dist',
                dest: '../../../target/gedoplan-webclients/angular/'
            }
        },
        watch: {
            development: {
                files: ['bower.json', 'app/index.html', 'app/**/*.js', 'app/**/*.html', 'app/assets/**/*.css'],
                options: {
                    livereload: true,
                }
            }
        },
    });

    grunt.loadNpmTasks('grunt-contrib-connect');
    grunt.loadNpmTasks('grunt-usemin');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-filerev');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-ng-annotate');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-maven-tasks');
    grunt.loadNpmTasks('grunt-connect-proxy');

    grunt.registerTask('build:usemin', [
        'clean:dist',
        'copy:html',
        'copy:index',
        'copy:assets',
        'ngAnnotate:build',
        'useminPrepare',
        'concat',
        'uglify',
        'cssmin',
        'usemin'
    ]);

    grunt.registerTask("start", [
        'configureProxies:server',
        'connect:server',
        'watch:development'
    ]);

    grunt.registerTask("build", [
        'build:usemin',
        'copy:final',
        'clean:dist'
    ]);

};
