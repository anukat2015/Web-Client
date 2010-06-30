/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package frontend.view.report;

import com.hp.hpl.jena.query.ResultSetRewindable;
import com.hp.hpl.jena.rdf.model.Model;
import dk.semantic_web.diy.controller.Form;
import dk.semantic_web.diy.controller.Error;
import javax.xml.transform.TransformerConfigurationException;
import model.SDB;
import frontend.controller.resource.report.ReportResource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import view.FormResultView;
import view.QueryStringBuilder;
import view.QueryResult;
import view.XMLSerializer;

/**
 *
 * @author Pumba
 */
public class ReportUpdateView extends ReportView implements FormResultView
{
    private Form form = null;
    private List<Error> errors = null;
    private Boolean result = null;
    private ResultSetRewindable queryResults = null;
    private Model model = null;

    public ReportUpdateView(ReportResource resource) throws TransformerConfigurationException
    {
	super(resource);
	setStyleSheet(new File(getController().getServletConfig().getServletContext().getRealPath("/xslt/report/ReportCreateView.xsl")));
    }

    @Override
    public ReportResource getResource()
    {
	return (ReportResource)super.getResource();
    }
    
    @Override
    public void display(HttpServletRequest request, HttpServletResponse response) throws IOException, TransformerException, ParserConfigurationException
    {
        setEndpoints(QueryResult.select(SDB.getDataset(), QueryStringBuilder.build(getController().getServletConfig().getServletContext().getRealPath("/sparql/report/endpoints.rq"))));
        setDataTypes(QueryResult.select(SDB.getDataset(), QueryStringBuilder.build(getController().getServletConfig().getServletContext().getRealPath("/sparql/ontology/data-types.rq"))));

        //setComments(QueryResult.select(SDB.getDataset(), QueryStringBuilder.build(getController().getServletConfig().getServletContext().getRealPath("/sparql/report/read/comments.rq"), getResource().getAbsoluteURI())));

        if (getResult() != null) // as in Create
        {
            String reportUri = request.getParameter("report-uri"); // after 1st request ("Query" submit), $report-uri is known
            setReport(QueryResult.select(getModel(), QueryStringBuilder.build(getController().getServletConfig().getServletContext().getRealPath("/sparql/report/read/report.rq"), reportUri)));

            getTransformer().setParameter("query-result", getResult());
            
            if (getResult())
            {
                setVisualizations(QueryResult.select(getModel(), QueryStringBuilder.build(getController().getServletConfig().getServletContext().getRealPath("/sparql/report/read/visualizations.rq"), reportUri)));
                setBindings(QueryResult.select(getModel(), QueryStringBuilder.build(getController().getServletConfig().getServletContext().getRealPath("/sparql/report/read/bindings.rq"), getResource().getAbsoluteURI())));
                setVariables(QueryResult.select(getModel(), QueryStringBuilder.build(getController().getServletConfig().getServletContext().getRealPath("/sparql/report/read/variables.rq"), reportUri)));

                getResolver().setArgument("results", XMLSerializer.serialize(getQueryResults()));
            }
            else
            {
                getResolver().setArgument("query-errors", XMLSerializer.serialize(getErrors()));
            }
        }
        else // as in Read
        {
            //setQueryResults(QueryResult.selectRemote(getResource().getReport().getQuery().getEndpoint().toString(), getResource().getReport().getQuery().getQueryString()));
            setReport(QueryResult.select(SDB.getDataset(), QueryStringBuilder.build(getController().getServletConfig().getServletContext().getRealPath("/sparql/report/read/report.rq"), getResource().getAbsoluteURI())));
            setVisualizations(QueryResult.select(SDB.getDataset(), QueryStringBuilder.build(getController().getServletConfig().getServletContext().getRealPath("/sparql/report/read/visualizations.rq"), getResource().getAbsoluteURI())));
            setBindings(QueryResult.select(SDB.getDataset(), QueryStringBuilder.build(getController().getServletConfig().getServletContext().getRealPath("/sparql/report/read/bindings.rq"), getResource().getAbsoluteURI())));
            setVariables(QueryResult.select(SDB.getDataset(), QueryStringBuilder.build(getController().getServletConfig().getServletContext().getRealPath("/sparql/report/read/variables.rq"), getResource().getAbsoluteURI())));

            getResolver().setArgument("results", XMLSerializer.serialize(getQueryResults()));
        }

	super.display(request, response);

	response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void setDataTypes(ResultSetRewindable dataTypes)
    {
	getResolver().setArgument("data-types", XMLSerializer.serialize(dataTypes));
    }

    @Override
    public Boolean getResult()
    {
        return result;
    }

    @Override
    public void setResult(Boolean result)
    {
        this.result = result;
    }

    @Override
    public List<Error> getErrors()
    {
        return errors;
    }

    @Override
    public void setErrors(List<Error> errors)
    {
        this.errors = errors;
    }

    public Model getModel()
    {
        return model;
    }

    public void setModel(Model model)
    {
        this.model = model;
    }

    @Override
    public Form getForm()
    {
        return form;
    }

    @Override
    public void setForm(Form form)
    {
        this.form = form;
    }

}
