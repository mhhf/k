// Copyright (c) 2015 K Team. All Rights Reserved.
package org.kframework.krun.modes;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import jline.ArgumentCompletor;
import jline.Completor;
import jline.ConsoleReader;
import jline.FileNameCompletor;
import jline.MultiCompletor;
import jline.SimpleCompletor;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.kframework.ExecutionMode;
import org.kframework.Rewriter;
import org.kframework.backend.unparser.PrintTransition;
import org.kframework.kil.Definition;
import org.kframework.kil.StringBuiltin;
import org.kframework.kore.K;
import org.kframework.krun.KRunDebuggerOptions;
import org.kframework.krun.KRunExecutionException;
import org.kframework.krun.api.KRunGraph;
import org.kframework.krun.api.SearchResults;
import org.kframework.utils.errorsystem.KEMException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by manasvi on 6/10/15.
 */
public class DebugExecutionMode implements ExecutionMode<Void> {

    private static Object command(JCommander jc) {
        return jc.getCommands().get(jc.getParsedCommand()).getObjects().get(0);

    }
    @Override
    public Void execute(K k, Rewriter rewriter) {
        /* Development Purposes Only, will go away in production */
        ConsoleReader reader;
        try {
            reader = new ConsoleReader();
        } catch (IOException e) {
            throw KEMException.internalError("IO error detected interacting with console", e);
        }
        reader.setBellEnabled(false);

        List<Completor> argCompletor = new LinkedList<Completor>();
        argCompletor.add(new SimpleCompletor(new String[] { "help",
                "exit", "resume", "step", "search", "select",
                "show-graph", "show-state", "show-transition", "save", "load", "read" }));
        argCompletor.add(new FileNameCompletor());
        List<Completor> completors = new LinkedList<Completor>();
        completors.add(new ArgumentCompletor(argCompletor));
        reader.addCompletor(new MultiCompletor(completors));
        while (true) {
            System.out.println();
            String input;
            try {
                input = reader.readLine("Command > ");
            } catch (IOException e) {
                throw KEMException.internalError("IO error detected interacting with console", e);
            }
            if (input == null) {
                // probably pressed ^D
                System.out.println();
                return null;
            }
            if (input.equals("")) {
                continue;
            }

            KRunDebuggerOptions options = new KRunDebuggerOptions();
            JCommander jc = new JCommander(options);
            jc.addCommand(options.help);
            jc.addCommand(options.step);
            jc.addCommand(options.search);
            jc.addCommand(options.select);
            jc.addCommand(options.showGraph);
            jc.addCommand(options.showState);
            jc.addCommand(options.showTransition);
            jc.addCommand(options.resume);
            jc.addCommand(options.exit);
            jc.addCommand(options.save);
            jc.addCommand(options.load);
            jc.addCommand(options.read);

            try {
                jc.parse(input.split("\\s+"));

                if (jc.getParsedCommand().equals("help")) {
                    if (options.help.command == null || options.help.command.size() == 0) {
                        jc.usage();
                    } else {
                        for (String command : options.help.command) {
                            if (jc.getCommands().containsKey(command)) {
                                jc.usage(command);
                            }
                        }
                    }
                } else if (command(jc) instanceof KRunDebuggerOptions.CommandExit) {
                    return null;

                } else if (command(jc) instanceof KRunDebuggerOptions.CommandResume) {


                } else if (command(jc) instanceof KRunDebuggerOptions.CommandStep) {


                } else if (command(jc) instanceof KRunDebuggerOptions.CommandSearch) {


                } else if (command(jc) instanceof KRunDebuggerOptions.CommandSelect) {


                } else if (command(jc) instanceof KRunDebuggerOptions.CommandShowGraph) {


                } else if (command(jc) instanceof KRunDebuggerOptions.CommandShowState) {


                } else if (command(jc) instanceof KRunDebuggerOptions.CommandShowTransition) {

                } else if (command(jc) instanceof KRunDebuggerOptions.CommandSave) {

                } else if (command(jc) instanceof KRunDebuggerOptions.CommandLoad) {

                } else if (command(jc) instanceof KRunDebuggerOptions.CommandRead) {

                } else {
                    assert false : "Unexpected krun debugger command " + jc.getParsedCommand();
                }
            } catch (ParameterException e) {
                System.err.println(e.getMessage());
                jc.usage();
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
