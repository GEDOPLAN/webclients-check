module.exports = function (grunt) {
    require('load-grunt-tasks')(grunt);
    var proxySnippet = require('grunt-connect-proxy/lib/utils').proxyRequest;
    grunt.initConfig({
        jshint: {
            options: {
                jshintrc: '.jshintrc'
            },
            all: {
                src: "app/{,components/**/, services/, resources/}*.js"
            }
        },
        clean: {
            tmp: ['.tmp', 'app/*.tmp', 'app/.tmp']
        },
        injector: {
            options: {
                bowerPrefix: 'bower:',
                template: 'app/index.html',
                ignorePath: 'app',
                addRootSlash: false,
                lineEnding: grunt.util.linefeed
            },
            development: {
                files: {
                    'app/index.html': [
                        'bower.json', 
                        'app/{,components/**/,services/,resources/}*.js', 
                        'app/bower_components/font-awesome/css/font-awesome.css',
                        'app/assets/styles/template.css',
                        'app/assets/styles/main.css'
                    ]
                }
            },
            annotated: {
                files: {
                    'app/index.html': ['bower.json', 'app/.tmp/scripts/**/*.js', 'app/assets/styles/*.css']
                }
            },
            build: {
                options: {
                    template: 'dist/index.html',
                    ignorePath: 'dist',
                },
                files: {
                    'dist/index.html': ['dist/assets/**/*.css', 'dist/scripts/*.js']
                }
            }
        },
        ngAnnotate: {
            build: {
                files: [{
                        expand: true,
                        src: 'app/{,components/**/,services/,resources/}*.js',
                        ext: '.annotated.js',
                        dest: 'app/.tmp/scripts'
                    }]
            }
        },
        watch: {
            index: {
                files: ['bower.json', 'app/index.html', 'app/**/*.js', 'app/**/*.html', 'app/assets/**/*.css'],
                options: {
                    livereload: true,
                }
            }
        },
        copy: {
            build: {
                expand: true,
                cwd: 'app',
                src: [
                    '{,components/**/}*.html',
                    'assets/{fonts,img,i18n}/**'
                ],
                dest: 'dist/'
            },
            final: {
                expand: true,
                cwd: 'dist',
                src: '**/*',
                dest: '../../../target/gedoplan-jax/angular/'
            }
        },
        connect: {
            server: {
                options: {
                    port: 3000,
                    base: 'app',
                    keepalive: false,
                    middleware: function (connect, options, middlewares) {
                        middlewares.unshift(require('grunt-connect-proxy/lib/utils').proxyRequest);
                        return middlewares;
                    }
                },
                proxies: [
                    {
                        context: ['/webresources', '/bvrules'],
                        rewrite: {
                            '^/bvrules': '/gedoplan-jax/bvrules',
                            '^/webresources': '/gedoplan-jax/webresources'
                        },
                        host: 'localhost',
                        port: 8080,
                        https: false,
                        xforward: true
                    }
                ]
            }
        },
        uglify: {
            generated: {
                options: {
                    sourceMap: true
                }
            }
        },
        useminPrepare: {
            html: 'app/index.html',
            options: {
                staging: 'app/.tmp',
                dest: 'dist'
            }
        },
        usemin: {
            html: ['dist/index.html'],
            css: ['dist/assets/styles/*.css'],
            js: ['dist/scripts/*.js'],
            options: {
                assetsDirs: ['dist', 'dist/assets/styles', 'dist/scripts']
            }
        }
    });
    grunt.registerTask("development", [
        'injector:development',
        'configureProxies:server',
        'connect:server',
        'watch:index'

    ]);
    grunt.registerTask('build', [
        'clean:tmp',
        'injector:development',
        'jshint:all',
        'ngAnnotate:build',
        'injector:annotated',
        'useminPrepare',
        'concat:generated',
        'cssmin:generated',
        'uglify:generated',
        'copy:build',
        'usemin',
        'injector:build',
        'copy:final',
        'clean:tmp',
        'injector:development'
    ]);
};