XDoclet has a concept called "merge points" that allows you to create files
that will be included into the final XML artifact you are creating.  This is a
list of possible files (in merged order) that can be used as merge-points in 
struts-resume.

For the filenames with a "{0}", this is not a typo. When XDoclet looks for the 
merge file, it will first substitute {0} with the unqualified name of the 
current class.

web.xml
====================================
web-settings.xml
filters.xml
filter-mappings.xml
listeners.xml
servlets.xml
web-sec-rolerefs-{0}.xml
servlet-mappings.xml
mime-mappings.xml
welcomefiles.xml
error-pages.xml
taglibs.xml
web-resource-env-refs.xml
ejb-resourcerefs.xml
ejb-resourcerefs-{0}.xml
web-security.xml
web-sec-roles.xml
web-env-entries.xml
web-env-entries-{0}.xml
web-ejbrefs.xml
web-ejbrefs-{0}.xml
web-ejbrefs-local.xml
web-ejbrefs-local-{0}.xml

struts-config.xml
====================================
struts-data-sources.xml
struts-forms.xml
global-exceptions.xml
global-forwards.xml
struts-actions.xml
actions.xml
struts-controller.xml
struts-message-resources.xml
struts-plugins.xml

validation.xml
====================================
validation-global.xml

