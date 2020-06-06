Problem - Huge amount of data in different format which need to be parsed to the same format to be useful
It is for collecting data for statistics without it's relations.
(Only collect for example name of cities and than can return those names, how much time some name appeared etc)
All input data need to be represented as key-value pairs in different serialization formats.
1.Send String in different formats (html, separated string, etc.)
2.Parse those formats into files some tag as key and data as value
- read path where to write them from config file (OUTPUT PATH)
- first make parsing of key value pars from different formats, than keep them in files of key name and containing values
- add validator of correctness of data (np if some value is number etc)
correctness of format if it can be parse to key value
add there logging of those which were parsed and which can't be parsed with name of failed validation
3. Get those information back as list.
4. Try to integrate it with Azure.