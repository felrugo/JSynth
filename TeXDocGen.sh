mkdir texdoclet_output

rm texdoclet_output/TeXDoclet.aux

javadoc -docletpath ./TeXDoclet.jar \
	-doclet org.stfm.texdoclet.TeXDoclet \
	-noindex \
	-hyperref \
	-texinit texdoclet_include/preamble.tex \
	-texsetup texdoclet_include/setup.tex \
	-texintro texdoclet_include/intro.tex \
	-texfinish texdoclet_include/finish.tex \
	-imagespath ".." \
	-output texdoclet_output/TeXDoclet.tex \
	-sourcepath src \
	-subpackages main:gui \
	-shortinherited \
	-sectionlevel section \
	-include \
	-nosummaries


